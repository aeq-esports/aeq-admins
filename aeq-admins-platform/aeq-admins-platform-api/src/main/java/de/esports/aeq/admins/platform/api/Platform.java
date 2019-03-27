package de.esports.aeq.admins.platform.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

public class Platform {

    private Long id;
    private String type;
    private String name;

    private Collection<PlatformInstance> instances = new ArrayList<>();

    /**
     * Obtains the unique id that identifies this platform.
     *
     * @return the platform id, not <code>null</code>
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtains the type of this platform.
     *
     * @return the type, not <code>null</code>
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtains a name that describes this platform.
     *
     * @return a message, not <code>null</code>
     */
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
        if (!(o instanceof Platform)) return false;
        Platform platform = (Platform) o;
        return Objects.equals(id, platform.id) &&
                Objects.equals(type, platform.type) &&
                Objects.equals(name, platform.name) &&
                Objects.equals(instances, platform.instances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name, instances);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Platform.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("type='" + type + "'")
                .add("name='" + name + "'")
                .add("instances=" + instances)
                .toString();
    }
}
