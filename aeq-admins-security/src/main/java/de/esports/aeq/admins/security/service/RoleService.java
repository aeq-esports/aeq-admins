package de.esports.aeq.admins.security.service;

import de.esports.aeq.admins.security.domain.RoleTa;

import java.util.List;

public interface RoleService {

    RoleTa create(RoleTa entity);

    RoleTa update(RoleTa entity);

    void delete(Long id);

    List<RoleTa> findAll();

    RoleTa findOne(Long id);

    RoleTa findByName(String name);
}
