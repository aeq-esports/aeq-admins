package de.esports.aeq.admins.security.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.exception.DuplicateUsernameException;
import de.esports.aeq.admins.security.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceBean implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceBean(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserTa register(UserTa entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserTa create(UserTa entity) {
        repository.findOneByUsername(entity.getUsername()).ifPresent(userTa -> {
            throw new DuplicateUsernameException();
        });

        String encoded = encoder.encode(entity.getPassword());
        entity.setPassword(encoded);
        return repository.save(entity);
    }

    @Override
    public List<UserTa> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<UserTa> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Collection<UserTa> findAllByIds(Collection<Long> userIds) {
        return repository.findAllById(userIds);
    }

    @Override
    public UserTa findByUsername(String username) {
        return repository.findOneByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username));
    }
}
