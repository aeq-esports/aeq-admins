package de.esports.aeq.admins.security.api.service;

import de.esports.aeq.admins.security.api.UserRole;

import java.util.List;

public interface RoleService {

    UserRole create(UserRole entity);

    UserRole update(UserRole entity);

    void delete(Long id);

    List<UserRole> findAll();

    UserRole findOne(Long id);

    UserRole findByName(String name);
}
