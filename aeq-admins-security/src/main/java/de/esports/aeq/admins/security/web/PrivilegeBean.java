package de.esports.aeq.admins.security.web;

import de.esports.aeq.admins.security.domain.PrivilegeTa;
import de.esports.aeq.admins.security.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class PrivilegeBean {

    private final PrivilegeService service;

    @Autowired
    public PrivilegeBean(PrivilegeService service) {
        this.service = service;
    }

    @PostConstruct
    private void registerPrivileges() {
        Arrays.stream(Privileges.values())
                .map(e -> PrivilegeTa.builder(e.toString()).build())
                .forEach(service::createIfNotExists);
    }
}
