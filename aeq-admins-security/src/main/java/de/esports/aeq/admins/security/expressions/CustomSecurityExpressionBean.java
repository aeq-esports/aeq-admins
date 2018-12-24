package de.esports.aeq.admins.security.expressions;

import de.esports.aeq.admins.security.domain.UserTa;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Configuration("cse")
public class CustomSecurityExpressionBean {

    public boolean hasUserId(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        UserTa user = (UserTa) authentication.getPrincipal();
        if (user == null) {
            return false;
        }

        return user.getId().equals(userId);
    }
}
