package de.esports.aeq.admins.security.impl;

import de.esports.aeq.admins.security.api.service.AppUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("cse")
public class CustomSecurityExpressionBean {

    private final AppUserService service;

    public CustomSecurityExpressionBean(AppUserService service) {
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
        return service.getByUsername(details.getUsername())
            .map(user -> user.getId().equals(userId)).orElse(false);
    }
}
