package de.esports.aeq.admins.security;

import org.springframework.security.core.GrantedAuthority;

public interface GrantedAuthorityHolder {

    GrantedAuthority toGrantedAuthority();
}
