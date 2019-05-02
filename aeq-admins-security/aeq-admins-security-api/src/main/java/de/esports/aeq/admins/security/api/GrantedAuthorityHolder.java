package de.esports.aeq.admins.security.api;

import org.springframework.security.core.GrantedAuthority;

public interface GrantedAuthorityHolder {

    GrantedAuthority toGrantedAuthority();
}
