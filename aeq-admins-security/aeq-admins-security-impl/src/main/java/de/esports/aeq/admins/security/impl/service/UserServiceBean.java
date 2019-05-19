package de.esports.aeq.admins.security.impl.service;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import de.esports.aeq.admins.common.Referable;
import de.esports.aeq.admins.security.api.DefaultUser;
import de.esports.aeq.admins.security.api.event.UserCreatedEvent;
import de.esports.aeq.admins.security.api.event.UserReferredEvent;
import de.esports.aeq.admins.security.api.exception.DuplicateUsernameException;
import de.esports.aeq.admins.security.api.service.AppUserService;
import de.esports.aeq.admins.security.impl.jpa.UserRepository;
import de.esports.aeq.admins.security.impl.jpa.entity.UserTa;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceBean implements AppUserService {

    private final ModelMapper mapper;
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    private KafkaTemplate<String, Object> template;

    @Autowired
    public UserServiceBean(ModelMapper mapper, UserRepository repository,
        PasswordEncoder encoder) {
        this.mapper = mapper;
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public DefaultUser create(DefaultUser user) {
        requireNonNull(user);
        user.setId(null);

        requireNonNull(user.getUsername());
        getByUsername(user.getUsername()).ifPresent(e -> {
            throw new DuplicateUsernameException(e.getUsername());
        });

        UserTa entity = mapper.map(user, UserTa.class);

        String encodedPassword = encoder.encode(entity.getPassword());
        entity.setPassword(encodedPassword);

        repository.save(entity);

        DefaultUser result = mapper.map(entity, DefaultUser.class);
        // credentials should not be passed back
        result.eraseCredentials();

        UserCreatedEvent event = new UserCreatedEvent(user);
        template.send(UserCreatedEvent.KEY, event);

        return user;
    }

    @Override
    public DefaultUser create(DefaultUser user, Referable referable) {
        requireNonNull(user);
        requireNonNull(referable);

        DefaultUser created = create(user);

        UserReferredEvent event = new UserReferredEvent(created, referable);
        template.send(UserReferredEvent.KEY, event);

        return created;
    }

    @Override
    public Optional<DefaultUser> getByUsername(String username) {
        return repository.findOneByUsername(username).map(this::toDefaultUser);
    }

    @Override
    public Collection<DefaultUser> getAll() {
        return repository.findAll().stream().map(this::toDefaultUser).collect(toList());
    }

    @Override
    public List<DefaultUser> getAll(Sort sort) {
        return repository.findAll(sort).stream().map(this::toDefaultUser).collect(toList());
    }

    @Override
    public Page<DefaultUser> getAll(Pageable pageable) {
        Page<UserTa> page = repository.findAll(pageable);
        List<DefaultUser> mapped = page.get().map(this::toDefaultUser).collect(toList());
        return new PageImpl<>(mapped, page.getPageable(), page.getTotalElements());
    }

    @Override
    public <S extends DefaultUser> List<S> getAll(Example<S> example) {
        UserTa probe = mapper.map(example.getProbe(), UserTa.class);
        Example<UserTa> mappedExample = Example.of(probe);

        return repository.findAll(mappedExample).stream()
            .map(userTa -> mapper.map(userTa, example.getProbeType())).collect(toList());
    }

    @Override
    public <S extends DefaultUser> List<S> getAll(Example<S> example, Sort sort) {
        UserTa probe = mapper.map(example.getProbe(), UserTa.class);
        Example<UserTa> mappedExample = Example.of(probe);

        return repository.findAll(mappedExample, sort).stream()
            .map(userTa -> mapper.map(userTa, example.getProbeType())).collect(toList());
    }

    @Override
    public Collection<DefaultUser> getAllByIds(Collection<Long> userIds) {
        return repository.findAllById(userIds).stream().map(this::toDefaultUser).collect(toList());
    }

    @Override
    public Optional<DefaultUser> getOneById(Long userId) {
        return repository.findById(userId).map(this::toDefaultUser);
    }

    private DefaultUser toDefaultUser(UserTa entity) {
        return mapper.map(entity, DefaultUser.class);
    }
}
