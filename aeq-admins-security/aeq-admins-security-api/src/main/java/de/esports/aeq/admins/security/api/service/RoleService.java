package de.esports.aeq.admins.security.api.service;

import de.esports.aeq.admins.security.api.Role;

import java.util.List;

public interface RoleService {

    Role create(Role entity);

    Role update(Role entity);

    void delete(Long id);

    List<Role> findAll();

    Role findOne(Long id);

    Role findByName(String name);
}
