package de.esports.aeq.admins.security.api.event;

import static java.util.stream.Collectors.toList;

import de.esports.aeq.admins.security.api.User;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class UserUpdatedEvent {

    public static final String KEY = "user.updated";

    private User previous;
    private User updated;

    public UserUpdatedEvent(User previous, User updated) {
        this.previous = previous;
        this.updated = updated;
    }

    public Collection<GrantedAuthority> getAddedUserRoles() {
        return updated.getUserRoles().stream()
            .filter((role) -> !previous.getUserRoles().contains(role)).collect(toList());
    }

    public Collection<GrantedAuthority> getRemovedUserRoles() {
        return previous.getUserRoles().stream()
            .filter((role) -> !updated.getUserRoles().contains(role)).collect(toList());
    }

    public User getPrevious() {
        return previous;
    }

    public User getUpdated() {
        return updated;
    }
}
