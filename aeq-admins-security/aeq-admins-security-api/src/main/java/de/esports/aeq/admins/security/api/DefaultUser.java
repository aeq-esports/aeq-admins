package de.esports.aeq.admins.security.api;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.Instant;
import java.util.Collection;
import java.util.Objects;

public class DefaultUser extends User {

    private Long id;
    private String email;
    private boolean verified;
    private Instant createdAt;

    public DefaultUser(String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultUser)) return false;
        if (!super.equals(o)) return false;
        DefaultUser that = (DefaultUser) o;
        return verified == that.verified &&
                Objects.equals(id, that.id) &&
                Objects.equals(email, that.email) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override public int hashCode() {
        return Objects.hash(super.hashCode(), id, email, verified, createdAt);
    }
}
