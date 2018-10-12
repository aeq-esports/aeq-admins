package de.esports.aeq.admins.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceBean implements UserDetailsService {

    private final UserService service;

    @Autowired
    public UserDetailsServiceBean(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return service.findByUsername(username);
    }
}