package de.esports.aeq.admins.security.web.types;

import de.esports.aeq.admins.security.domain.RoleTa;

import java.util.HashSet;
import java.util.Set;

public class UserResponseDTO {

    private Long id;
    private String email;
    private String username;
    private String ts3UId;
    private Set<RoleTa> roles = new HashSet<>();

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTs3UId() {
        return ts3UId;
    }

    public void setTs3UId(String ts3UId) {
        this.ts3UId = ts3UId;
    }

    public Set<RoleTa> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleTa> roles) {
        this.roles = roles;
    }
}
