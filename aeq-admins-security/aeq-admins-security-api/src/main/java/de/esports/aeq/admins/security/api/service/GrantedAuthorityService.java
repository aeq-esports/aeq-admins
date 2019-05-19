package de.esports.aeq.admins.security.api.service;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public interface GrantedAuthorityService {

    Collection<GrantedAuthority> getAuthorities();
}
