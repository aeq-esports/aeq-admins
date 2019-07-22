package de.esports.aeq.admins.member.api.event;

import de.esports.aeq.admins.member.api.UserProfile;

public class UserProfileUpdatedEvent {

    public static final String KEY = "user.profile.updated";

    private UserProfile previous;
    private UserProfile updated;

    public UserProfileUpdatedEvent(UserProfile previous, UserProfile updated) {
        this.previous = previous;
        this.updated = updated;
    }

    public UserProfile getPrevious() {
        return previous;
    }

    public UserProfile getUpdated() {
        return updated;
    }
}
