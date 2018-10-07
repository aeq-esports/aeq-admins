package de.esports.aeq.admins.security.service;

import de.esports.aeq.admins.security.domain.UserTa;

import java.util.List;

public interface UserService {

    UserTa create(UserTa entity);

    List<UserTa> findAll();

    UserTa findByUsername(String username);
}
