package de.esports.aeq.admins.security.service;

import de.esports.aeq.admins.security.domain.UserTa;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserTa register(UserTa entity);

    UserTa create(UserTa entity);

    List<UserTa> findAll();

    Optional<UserTa> findById(Long id);

    Collection<UserTa> findAllByIds(Collection<Long> userIds);

    UserTa findByUsername(String username);
}
