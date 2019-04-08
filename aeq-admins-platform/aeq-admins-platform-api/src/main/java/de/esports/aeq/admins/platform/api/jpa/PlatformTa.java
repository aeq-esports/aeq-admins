package de.esports.aeq.admins.platform.api.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringJoiner;

@Entity
@Table(name = "aeq_platform")
public class PlatformTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "plt_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String type;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Collection<PlatformInstanceTa> instances = new ArrayList<>();

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<PlatformInstanceTa> getInstances() {
        return instances;
    }

    public void setInstances(
            Collection<PlatformInstanceTa> instances) {
        this.instances = instances;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PlatformTa.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("type='" + type + "'")
                .toString();
    }
}
