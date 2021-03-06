package de.esports.aeq.admins.security.api.event;

import static java.util.Objects.requireNonNull;

import de.esports.aeq.admins.security.api.User;
import java.util.Objects;
import java.util.StringJoiner;

public class UserCreatedEvent {

    public static final String KEY = "user.created";

    private User user;

    public UserCreatedEvent() {
        // needed for serialisation
    }

    public UserCreatedEvent(User user) {
        this.user = requireNonNull(user, "The user must not be null");
    }

    public User getUser() {
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
