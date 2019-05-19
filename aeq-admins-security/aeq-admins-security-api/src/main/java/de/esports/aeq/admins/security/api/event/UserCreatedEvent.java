package de.esports.aeq.admins.security.api.event;

import de.esports.aeq.admins.security.api.DefaultUser;
import java.util.Objects;
import java.util.StringJoiner;

public class UserCreatedEvent {

    public static final String KEY = "user.created";

    private DefaultUser user;

    public UserCreatedEvent(DefaultUser user) {
        this.user = user;
    }

    public DefaultUser getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCreatedEvent)) {
            return false;
        }
        UserCreatedEvent event = (UserCreatedEvent) o;
        return Objects.equals(user, event.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserCreatedEvent.class.getSimpleName() + "[", "]")
            .add("user=" + user)
            .toString();
    }
}
