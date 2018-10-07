package de.esports.aeq.admins.security.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "aeq_privilege")
public class PrivilegeTa implements Serializable {

    public static final String PREFIX = "PRIVILEGE";

    @Id
    @GeneratedValue
    @Column(name = "privilege_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "privileges")
    @JsonBackReference
    private Set<RoleTa> roles = new HashSet<>();

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public PrivilegeTa() {

    }

    private PrivilegeTa(Builder builder) {
        this.name = builder.name;
        this.roles = builder.roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!name.startsWith(PREFIX))
            throw new IllegalArgumentException(
                    "Required prefix '" + PREFIX + "' does not match: " + name);
        this.name = name;
    }

    public Set<RoleTa> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleTa> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrivilegeTa)) return false;
        PrivilegeTa that = (PrivilegeTa) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static class Builder {

        private String name;
        private Set<RoleTa> roles = new HashSet<>();

        private Builder(String name) {
            this.name = name;
        }

        public Builder withRoles(Collection<? extends RoleTa> roles) {
            this.roles.addAll(roles);
            return this;
        }

        public PrivilegeTa build() {
            return new PrivilegeTa(this);
        }
    }
}
