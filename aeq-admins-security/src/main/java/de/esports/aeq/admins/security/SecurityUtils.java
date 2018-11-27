package de.esports.aeq.admins.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static void assertPrivileges(GrantedAuthorityHolder... requiredAuthorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required");
        }

        var authorities = authentication.getAuthorities();
        for (GrantedAuthorityHolder holder : requiredAuthorities) {
            GrantedAuthority authority = holder.toGrantedAuthority();
            if (!authorities.contains(authority)) {
                throw new AccessDeniedException("Required privilege not found: " + authority);
            }
        }
    }

    private SecurityUtils() {

    }
}
