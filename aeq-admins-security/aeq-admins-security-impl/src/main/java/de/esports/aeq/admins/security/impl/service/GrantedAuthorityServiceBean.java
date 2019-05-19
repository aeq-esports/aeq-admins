package de.esports.aeq.admins.security.impl.service;

import de.esports.aeq.admins.security.api.JpaGrantedAuthority;
import de.esports.aeq.admins.security.api.service.GrantedAuthorityService;
import de.esports.aeq.admins.security.impl.jpa.PrivilegeRepository;
import de.esports.aeq.admins.security.impl.jpa.entity.PrivilegeTa;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class GrantedAuthorityServiceBean implements GrantedAuthorityService {

    private final PrivilegeRepository repository;

    public GrantedAuthorityServiceBean(PrivilegeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return repository.findAll().stream().map(this::toGrantedAuthority)
            .collect(Collectors.toList());
    }

    private GrantedAuthority toGrantedAuthority(PrivilegeTa privilegeTa) {
        return new JpaGrantedAuthority(privilegeTa.getId(), privilegeTa.getName());
    }
}
