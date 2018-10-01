package de.esports.aeq.admins.security.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.exception.DuplicateUsernameException;
import de.esports.aeq.admins.security.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
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
    public UserTa findOneByUsername(String username) {
        return repository.findOneByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
    }
}
