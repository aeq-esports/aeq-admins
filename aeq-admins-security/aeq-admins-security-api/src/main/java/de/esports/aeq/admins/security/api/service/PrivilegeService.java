package de.esports.aeq.admins.security.api.service;

import de.esports.aeq.admins.security.api.Privilege;

import java.util.List;

public interface PrivilegeService {

    Privilege createIfNotExists(Privilege entity);

    List<Privilege> findAll();

    Privilege findOneByName(String name);
}
