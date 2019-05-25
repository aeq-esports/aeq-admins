package de.esports.aeq.admins.security.api;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;

public class UserRole implements GrantedAuthority {

    public static final String PREFIX = "ROLE_";

    private static final String NAME_NOT_NULL = "The name must not be null";
    private static final String NAME_NOT_BLANK = "The name must not be blank";

    public static boolean isUserRole(GrantedAuthority authority) {
        return authority.getAuthority().startsWith(UserRole.PREFIX);
    }

    private static String addPrefix(String name) {
        requireNonNull(name, NAME_NOT_NULL);
        if (name.isBlank()) {
            throw new IllegalArgumentException(NAME_NOT_BLANK);
        }
        return name.toUpperCase().startsWith(PREFIX) ? name : PREFIX + name;
    }

    private final String name;
    private Set<GrantedAuthority> privileges = new HashSet<>();

    public UserRole(String name) {
        this.name = addPrefix(name);
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableSet(privileges);
    }

    public void addAuthority(GrantedAuthority authority) {
        this.privileges.add(authority);
    }
}
