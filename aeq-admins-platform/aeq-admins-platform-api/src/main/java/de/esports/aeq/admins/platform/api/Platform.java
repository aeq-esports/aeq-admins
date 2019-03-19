package de.esports.aeq.admins.platform.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

public class Platform implements Serializable {

    private Long id;
    private String type;
    private String name;

    private Collection<PlatformData> platformData = new ArrayList<>();

    public static Platform create(String type, String name) {
        return new Platform(type, name);
    }

    public Platform() {

    }

    private Platform(String type, String name) {
        this.type = requireNonNull(type);
        this.name = requireNonNull(name);
    }

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

    /**
     * @return a <i>modifiable</i> collection, not <code>null</code>
     */
    public Collection<PlatformData> getPlatformData() {
        return platformData;
    }

    public void setPlatformData(Collection<PlatformData> platformData) {
        this.platformData = platformData;
    }
}
