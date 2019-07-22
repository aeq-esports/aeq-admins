package de.esports.aeq.admins.security.api;

import de.esports.aeq.admins.security.api.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("cse")
public class CustomSecurityExpressions {

    private final SecurityService service;

    @Autowired
    public CustomSecurityExpressions(SecurityService service) {
        this.service = service;
    }

    public boolean hasUserId(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return hasUserId((UserDetails) principal, userId);
        }

        return false;
    }

    private boolean hasUserId(UserDetails details, Long userId) {
        return service.getOneByUsername(details.getUsername())
            .map(user -> user.getId().equals(userId)).orElse(Boolean.FALSE);
    }

    public boolean isPrincipal(String username) {
        if (username.isBlank()) {
            return false;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername().equals(username);
        }

        return false;
    }
}
