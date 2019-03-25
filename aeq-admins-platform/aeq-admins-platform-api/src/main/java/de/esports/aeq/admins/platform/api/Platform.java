package de.esports.aeq.admins.platform.api;

public class Platform {

    private Long id;
    private String type;
    private String name;

    private Class<?> instanceClass;

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

    public Class<?> getInstanceClass() {
        return instanceClass;
    }

    public void setInstanceClass(Class<?> instanceClass) {
        this.instanceClass = instanceClass;
    }
}
