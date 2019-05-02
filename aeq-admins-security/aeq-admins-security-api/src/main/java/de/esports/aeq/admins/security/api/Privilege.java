package de.esports.aeq.admins.security.api;

import org.springframework.security.core.GrantedAuthority;

public class Privilege implements GrantedAuthority {

    public static Privilege of(String name) {
        return new Privilege(name);
    }

    public static Privilege of(GrantedAuthority authority) {
        return of(authority.getAuthority());
    }

    private final String name;

    public Privilege(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
