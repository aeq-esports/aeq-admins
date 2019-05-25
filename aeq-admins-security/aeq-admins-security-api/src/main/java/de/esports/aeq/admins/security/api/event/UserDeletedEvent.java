package de.esports.aeq.admins.security.api.event;

import de.esports.aeq.admins.security.api.User;

public class UserDeletedEvent {

    public static final String KEY = "user.deleted";

    private User user;

    public UserDeletedEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
