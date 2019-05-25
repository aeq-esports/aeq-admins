package de.esports.aeq.admins.security.impl.service;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.security.api.User;
import de.esports.aeq.admins.security.api.UserRole;
import de.esports.aeq.admins.security.api.event.UserCreatedEvent;
import de.esports.aeq.admins.security.api.event.UserDeletedEvent;
import de.esports.aeq.admins.security.api.event.UserRoleDeletedEvent;
import de.esports.aeq.admins.security.api.event.UserUpdatedEvent;
import de.esports.aeq.admins.security.api.exception.DuplicateUsernameException;
import de.esports.aeq.admins.security.api.service.SecurityService;
import de.esports.aeq.admins.security.impl.jpa.PrivilegeRepository;
import de.esports.aeq.admins.security.impl.jpa.RoleRepository;
import de.esports.aeq.admins.security.impl.jpa.UserRepository;
import de.esports.aeq.admins.security.impl.jpa.entity.PrivilegeTa;
import de.esports.aeq.admins.security.impl.jpa.entity.RoleTa;
import de.esports.aeq.admins.security.impl.jpa.entity.UserTa;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceBean implements SecurityService {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityServiceBean.class);

    private static final String USER_NOT_NULL = "The user must not be null";
    private static final String USER_ID_NOT_NULL = "The user id must not be null";
    private static final String USERNAME_NOT_NULL = "The username must not be null";
    private static final String ROLE_NAME_NOT_NULL = "The role name must not be null";

    private final ModelMapper mapper;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private KafkaTemplate<String, Object> template;

    public SecurityServiceBean(ModelMapper mapper, UserRepository userRepository,
        RoleRepository roleRepository, PrivilegeRepository privilegeRepository,
        PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll().stream().map(this::toUser).collect(toList());
    }

    @Override
    public List<User> getUsers(Sort sort) {
        requireNonNull(sort);
        return userRepository.findAll(sort).stream().map(this::toUser).collect(toList());
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toUser);
    }

    @Override
    public Collection<User> getUsersByIds(Collection<Long> userIds) {
        return userRepository.findAllById(userIds).stream().map(this::toUser)
            .collect(toList());
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId).map(this::toUser);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findOneByUsername(username).map(this::toUser);
    }

    @Override
    public User createUser(User user) {
        requireNonNull(user, USER_NOT_NULL);

        user.setId(null);
        String username = requireNonNull(user.getUsername(), USERNAME_NOT_NULL);

        userRepository.findOneByUsername(username)
            .orElseThrow(() -> new DuplicateUsernameException(username));

        UserTa entity = toUserTa(user);

        String encodedPassword = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodedPassword);

        userRepository.save(entity);

        User result = toUser(entity);

        // credentials should not be passed back
        user.eraseCredentials();
        result.eraseCredentials();

        UserCreatedEvent event = new UserCreatedEvent(user);
        template.send(UserCreatedEvent.KEY, event);

        return result;
    }

    @Override
    public User updateUser(User user) {
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
    public void deleteUser(Long userId) {
        requireNonNull(userId, USER_ID_NOT_NULL);

        User user = userRepository.findById(userId).map(this::toUser)
            .orElseThrow(() -> new EntityNotFoundException(userId));

        userRepository.deleteById(user.getId());

        UserDeletedEvent event = new UserDeletedEvent(user);
        template.send(UserDeletedEvent.KEY, event);
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<UserRole> getRoles() {
        return roleRepository.findAll().stream().map(RoleTa::getName).map(UserRole::new)
            .collect(toList());
    }

    @Override
    public Optional<UserRole> getRoleByName(String name) {
        return roleRepository.findOneByName(name).map(RoleTa::getName).map(UserRole::new);
    }

    @Override
    public Collection<User> getUsersByRoleName(String name) {
        RoleTa userRole = roleRepository.findOneByName(name)
            .orElseThrow(() -> new EntityNotFoundException(name));
        return userRepository.findAllByRolesContains(userRole.getId()).stream()
            .map(this::toUser).collect(toList());
    }

    @Override
    public Collection<User> getUsersByAnyRoleName(Collection<String> roleNames) {
        Collection<Long> userRoleIds = roleRepository.findAllByNames(roleNames)
            .stream().map(RoleTa::getId).collect(toList());
        return userRepository.findAllByRolesContains(userRoleIds).stream()
            .map(this::toUser).collect(toList());
    }

    @Override
    public Collection<User> getUsersByAllRoleNames(Collection<String> roleNames) {
        return getUsersByAnyRoleName(roleNames).stream()
            .filter(user -> user.getUserRoles().containsAll(roleNames)).collect(toList());
    }

    @Override
    public UserRole createUserRole(UserRole role) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserRole updateUserRole(UserRole role) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUserRole(String name) {
        requireNonNull(name, ROLE_NAME_NOT_NULL);

        RoleTa entity = roleRepository.findOneByName(name)
            .orElseThrow(() -> new EntityNotFoundException(name));

        UserRole role = new UserRole(entity.getName());
        roleRepository.deleteById(entity.getId());

        UserRoleDeletedEvent event = new UserRoleDeletedEvent(role);
        template.send(UserRoleDeletedEvent.KEY, event);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> result = new HashSet<>(getRoles());

        privilegeRepository.findAll().stream().map(PrivilegeTa::getName)
            .map(SimpleGrantedAuthority::new).forEach(result::add);

        return result;
    }

    //-----------------------------------------------------------------------

    private User toUser(UserTa userTa) {
        return mapper.map(userTa, User.class);
    }

    private UserTa toUserTa(User user) {
        return mapper.map(user, UserTa.class);
    }
}
