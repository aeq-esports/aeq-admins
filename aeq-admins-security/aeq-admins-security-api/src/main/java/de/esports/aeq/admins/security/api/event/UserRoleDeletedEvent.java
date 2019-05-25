package de.esports.aeq.admins.security.api.event;

import de.esports.aeq.admins.security.api.UserRole;

public class UserRoleDeletedEvent {

    public static final String KEY = "user.role.deleted";

    public UserRoleDeletedEvent(UserRole role) {

    }
}
