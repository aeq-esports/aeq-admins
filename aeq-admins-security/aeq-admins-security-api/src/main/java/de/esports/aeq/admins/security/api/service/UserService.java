package de.esports.aeq.admins.security.api.service;

import de.esports.aeq.admins.security.api.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User register(User entity);

    User create(User entity);

    List<User> findAll();

    Optional<User> findById(Long id);

    Collection<User> findAllByIds(Collection<Long> userIds);

    User findByUsername(String username);
}
