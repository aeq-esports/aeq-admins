package de.esports.aeq.admins.security.service;

import de.esports.aeq.admins.security.domain.UserTa;

public interface UserService {

    UserTa create(UserTa entity);

    UserTa findOneByUsername(String username);
}
