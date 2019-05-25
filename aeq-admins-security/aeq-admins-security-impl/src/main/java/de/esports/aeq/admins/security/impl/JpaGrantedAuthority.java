package de.esports.aeq.admins.security.impl;

import java.util.Objects;
import java.util.StringJoiner;
import org.springframework.security.core.GrantedAuthority;

public class JpaGrantedAuthority implements GrantedAuthority {

    private final Long id;
    private final String name;

    public JpaGrantedAuthority(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JpaGrantedAuthority)) {
            return false;
        }
        JpaGrantedAuthority that = (JpaGrantedAuthority) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", JpaGrantedAuthority.class.getSimpleName() + "[", "]")
            .add("id='" + id + "'")
            .add("name='" + name + "'")
            .toString();
    }
}
