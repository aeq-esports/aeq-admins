package de.esports.aeq.admins.security.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "aeq_user")
@NamedEntityGraph(name = "graph.UserTa.roles.privileges",
        attributeNodes = @NamedAttributeNode(value = "roles", subgraph = "privileges"),
        subgraphs = @NamedSubgraph(
                name = "privileges", attributeNodes = @NamedAttributeNode("privileges")))
public class UserTa implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "ts3_uid")
    private String ts3UId;

    @ManyToMany
    @JoinTable(name = "aeq_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().flatMap(roleTa -> roleTa.getPrivileges().stream())
                .map(privilegeTa -> new SimpleGrantedAuthority(privilegeTa.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
