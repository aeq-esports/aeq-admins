package de.esports.aeq.admins.security.service;

import de.esports.aeq.admins.security.domain.UserTa;

import java.util.List;

public interface UserService {

    UserTa register(UserTa entity);

    UserTa create(UserTa entity);

    List<UserTa> findAll();

    UserTa findById(Long id);

    UserTa findByUsername(String username);
}
