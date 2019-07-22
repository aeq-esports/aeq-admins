package de.esports.aeq.admins.security.impl.service;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.security.api.User;
import de.esports.aeq.admins.security.api.event.UserCreatedEvent;
import de.esports.aeq.admins.security.api.event.UserDeletedEvent;
import de.esports.aeq.admins.security.api.event.UserUpdatedEvent;
import de.esports.aeq.admins.security.api.exception.DuplicateUsernameException;
import de.esports.aeq.admins.security.api.service.SecurityService;
import de.esports.aeq.admins.security.impl.jpa.UserRepository;
import de.esports.aeq.admins.security.impl.jpa.entity.UserTa;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceBean implements SecurityService {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityServiceBean.class);

    private static final String USER_NOT_NULL = "The user must not be null";
    private static final String USER_ID_NOT_NULL = "The user id must not be null";
    private static final String USERNAME_NOT_NULL = "The username must not be null";
    private static final String USER_ROLE_NAME_NOT_NULL = "The user role name must not be null";

    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private KafkaTemplate<String, Object> template;

    public SecurityServiceBean(ModelMapper mapper, UserRepository userRepository,
        PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //-----------------------------------------------------------------------

    @PostConstruct
    private void configureMapper() {
        mapper.createTypeMap(User.class, UserTa.class);
        mapper.createTypeMap(UserTa.class, User.class);
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<User> getAll() {
        return userRepository.findAll().stream().map(this::toUser).collect(toList());
    }

    @Override
    public List<User> getAll(Sort sort) {
        requireNonNull(sort);
        return userRepository.findAll(sort).stream().map(this::toUser).collect(toList());
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toUser);
    }

    @Override
    public List<User> getAll(Example<User> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> getAll(Example<User> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<User> getAllByIds(Collection<Long> userIds) {
        return userRepository.findAllById(userIds).stream().map(this::toUser).collect(toList());
    }

    @Override
    public Optional<User> getOneById(Long userId) {
        return userRepository.findById(userId).map(this::toUser);
    }

    @Override
    public Optional<User> getOne(User user) {
        return getOneById(user.getId());
    }

    @Override
    public Optional<User> getOneByUsername(String username) {
        requireNonNull(username, USERNAME_NOT_NULL);
        return userRepository.findOneByUsername(username).map(this::toUser);
    }

    @Override
    public User create(User user) {
        requireNonNull(user, USER_NOT_NULL);

        user.setId(null);
        String username = requireNonNull(user.getUsername(), USER_ROLE_NAME_NOT_NULL);

        userRepository.findOneByUsername(username).ifPresent(e -> {
            throw new DuplicateUsernameException(username);
        });

        UserTa entity = toUserTa(user);

        String encodedPassword = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodedPassword);

        userRepository.save(entity);

        User result = toUser(entity);

        // credentials should not be passed back
        user.eraseCredentials();
        result.eraseCredentials();

        UserCreatedEvent event = new UserCreatedEvent(result);
        template.send(UserCreatedEvent.KEY, event);

        return result;
    }

    @Override
    public User update(User user) {
        requireNonNull(user, USER_NOT_NULL);
        requireNonNull(user.getId(), USER_ID_NOT_NULL);

        UserTa existing = userRepository.findById(user.getId())
            .orElseThrow(() -> new EntityNotFoundException(user.getId()));

        UserTa update = toUserTa(user);
        mapper.map(update, existing);

        String encodedPassword = passwordEncoder.encode(existing.getPassword());
        existing.setPassword(encodedPassword);

        User result = toUser(existing);

        // credentials should not be passed back
        user.eraseCredentials();
        result.eraseCredentials();

        UserUpdatedEvent event = new UserUpdatedEvent(user, result);
        template.send(UserUpdatedEvent.KEY, event);

        return result;
    }

    @Override
    public void remove(Long userId) {
        requireNonNull(userId, USER_ID_NOT_NULL);

        User user = userRepository.findById(userId).map(this::toUser)
            .orElseThrow(() -> new EntityNotFoundException(userId));

        userRepository.deleteById(userId);

        UserDeletedEvent event = new UserDeletedEvent(user);
        template.send(UserDeletedEvent.KEY, event);
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<User> getAllByRoleName(String userRoleName) {
        requireNonNull(userRoleName, USER_ROLE_NAME_NOT_NULL);
        return userRepository.findAllByAuthoritiesContains(List.of(userRoleName)).stream()
            .map(this::toUser).collect(toList());
    }

    @Override
    public Collection<User> getAllByAnyRoleName(Collection<String> roleNames) {
        requireNonNull(roleNames);
        return userRepository.findAllByAuthoritiesContains(roleNames).stream()
            .map(this::toUser).collect(toList());
    }

    @Override
    public Collection<User> getAllByAllRoleNames(Collection<String> roleNames) {
        return getAllByAnyRoleName(roleNames).stream()
            .filter(user -> user.getUserRoles().containsAll(roleNames)).collect(toList());
    }

    //-----------------------------------------------------------------------

    private User toUser(UserTa userTa) {
        return mapper.map(userTa, User.class);
    }

    private UserTa toUserTa(User user) {
        return mapper.map(user, UserTa.class);
    }
}
