package de.esports.aeq.admins.security.impl.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import de.esports.aeq.admins.security.api.UserRoleFlag;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "aeq_sec_role")
@NamedEntityGraph(name = "graph.UserRoleTa.privileges",
    attributeNodes = @NamedAttributeNode(value = "privileges"))
public class UserRoleTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Collection<UserTa> users;


    private Map<String, Collection<GrantedAuthority>> authorities = new HashMap<>();
    private Set<UserRoleFlag> flags = EnumSet.noneOf(UserRoleFlag.class);


    @ManyToMany
    @JoinTable(name = "aeq_role_privilege",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    @JsonManagedReference
    private Set<PrivilegeTa> privileges = new HashSet<>();

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

    public Collection<UserTa> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserTa> users) {
        this.users = users;
    }

    public Set<PrivilegeTa> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<PrivilegeTa> privileges) {
        this.privileges = privileges;
    }
}
