package de.esports.aeq.admins.security.service;

import de.esports.aeq.admins.security.domain.PrivilegeTa;

import java.util.List;

public interface PrivilegeService {

    PrivilegeTa createIfNotExists(PrivilegeTa entity);

    List<PrivilegeTa> findAll();

    PrivilegeTa findOneByName(String name);
}
