package de.esports.aeq.admins.security.impl.support;

import de.esports.aeq.admins.security.api.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceBean implements UserDetailsService {

    private final SecurityService securityService;

    @Autowired
    public UserDetailsServiceBean(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return securityService.getOneByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}