package de.esports.aeq.admins.security.impl.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "aeq_sec_privilege")
public class PrivilegeTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "privilege_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "privileges")
    @JsonBackReference
    private Set<RoleTa> roles = new HashSet<>();

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
        this.name = name;
    }

    public Set<RoleTa> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleTa> roles) {
        this.roles = roles;
    }
}
