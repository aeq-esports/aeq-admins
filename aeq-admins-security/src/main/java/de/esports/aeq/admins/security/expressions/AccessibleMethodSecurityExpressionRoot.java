package de.esports.aeq.admins.security.expressions;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;

public class AccessibleMethodSecurityExpressionRoot extends SecurityExpressionRoot {

    public AccessibleMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean isAccessible(Long userId, String grantedAuthority) {
        return true;
    }
}
