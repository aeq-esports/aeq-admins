package de.esports.aeq.admins.security.web;

import de.esports.aeq.admins.security.domain.RoleTa;
import de.esports.aeq.admins.security.jpa.PrivilegeRepository;
import de.esports.aeq.admins.security.jpa.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;

@Component
public class RoleDefaultsBean {

    private static final Logger LOG = LoggerFactory.getLogger(RoleDefaultsBean.class);
    private static final String ROLE_ADMIN = "admin";

    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;

    @Autowired
    public RoleDefaultsBean(RoleRepository roleRepository,
            PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @PostConstruct
    private void setupRoles() {
        LOG.info("Checking for privileges to update for role {}", ROLE_ADMIN);
        roleRepository.findOneByName(ROLE_ADMIN)
                .ifPresentOrElse(this::updateMissingPrivileges, this::createAdminRole);
    }

    private void createAdminRole() {
        RoleTa admin = new RoleTa();
        admin.setName(ROLE_ADMIN);
        updateMissingPrivileges(admin);
    }

    private void updateMissingPrivileges(RoleTa role) {
        var privileges = new HashSet<>(privilegeRepository.findAll());
        if (privileges.equals(role.getPrivileges())) {
            LOG.info("{} role already has required privileges.", role.getName());
            return;
        }
        LOG.info("Updating privileges for role {}", role.getName());
        role.setPrivileges(privileges);
        roleRepository.save(role);
    }
}
