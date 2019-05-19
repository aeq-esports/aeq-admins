package de.esports.aeq.admins.security.impl.support;

import de.esports.aeq.admins.security.api.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceBean implements UserDetailsService {

    private final AppUserService service;

    @Autowired
    public UserDetailsServiceBean(AppUserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return service.getByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
