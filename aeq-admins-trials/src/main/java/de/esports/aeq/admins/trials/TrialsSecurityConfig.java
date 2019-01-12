package de.esports.aeq.admins.trials;

import de.esports.aeq.admins.security.domain.PrivilegeTa;
import de.esports.aeq.admins.security.service.PrivilegeService;
import de.esports.aeq.admins.trials.common.Privileges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class TrialsSecurityConfig {

    private PrivilegeService service;

    @Autowired
    public TrialsSecurityConfig(PrivilegeService service) {
        this.service = service;
    }

    @PostConstruct
    private void setupPrivileges() {
        Arrays.stream(Privileges.values())
                .map(privilege -> PrivilegeTa.builder(privilege.toGrantedAuthority().getAuthority()).build())
                .forEach(service::createIfNotExists);
    }
}
