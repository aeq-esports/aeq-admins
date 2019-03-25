package de.esports.aeq.admins.platform.impl.web.dto;

import de.esports.aeq.admins.platform.api.PlatformInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class PlatformDto {

    private Long id;
    private String type;
    private String name;

    private Collection<PlatformInstance> instances = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<PlatformInstance> getInstances() {
        return instances;
    }

    public void setInstances(Collection<PlatformInstance> instances) {
        this.instances = instances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlatformDto)) return false;
        PlatformDto that = (PlatformDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name);
    }
}
