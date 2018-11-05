package de.esports.aeq.admins.security.expressions;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("cse")
public class CustomSecurityExpressionBean {

    public boolean canAccessRole(Authentication authentication, Long userId) {
        return true;
    }
}
