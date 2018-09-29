package de.esports.aeq.admins.security.service;

import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserTaImpl implements UserService {

    private UserRepository repository;
    private PasswordEncoder encoder;

    @Autowired
    public UserTaImpl(UserRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserTa create(UserTa entity) {
        String encodedPassword = encoder.encode(entity.getPassword());
        entity.setPassword(encodedPassword);

        return repository.save(entity);
    }
}
