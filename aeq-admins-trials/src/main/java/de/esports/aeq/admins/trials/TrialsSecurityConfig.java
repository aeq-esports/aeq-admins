package de.esports.aeq.admins.trials;

import de.esports.aeq.admins.security.api.service.GrantedAuthorityService;
import de.esports.aeq.admins.trials.common.Privileges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class TrialsSecurityConfig {

    private GrantedAuthorityService service;

    @Autowired
    public TrialsSecurityConfig(GrantedAuthorityService service) {
        this.service = service;
    }

    @PostConstruct
    private void setupPrivileges() {
        Arrays.stream(Privileges.values()).map(Privileges::getName).map(Authority::of)
                .forEach(service::createIfNotExists);
    }
}
