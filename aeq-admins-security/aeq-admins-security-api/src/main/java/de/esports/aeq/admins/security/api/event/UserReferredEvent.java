package de.esports.aeq.admins.security.api.event;

import de.esports.aeq.admins.common.Referable;
import de.esports.aeq.admins.security.api.User;
import java.util.Objects;
import java.util.StringJoiner;

public class UserReferredEvent {

    public static final String KEY = "user.referred";

    private final User user;
    private final Referable referable;

    public UserReferredEvent(User user, Referable referable) {
        this.user = user;
        this.referable = referable;
    }

    public User getUser() {
        return user;
    }

    public Referable getReferable() {
        return referable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserReferredEvent)) {
            return false;
        }
        UserReferredEvent that = (UserReferredEvent) o;
        return Objects.equals(user, that.user) &&
            Objects.equals(referable, that.referable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, referable);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserReferredEvent.class.getSimpleName() + "[", "]")
            .add("user=" + user)
            .add("referable=" + referable)
            .toString();
    }
}
