package de.esports.aeq.admins.members.domain.account;

import java.io.Serializable;

public class Platform implements Serializable {

    private Long id;
    private String type;
    private String name;
    private Object data;

    /**
     * Obtains the unique id that identifies this platform.
     *
     * @return the platform id, not <code>null</code>
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtains the type of this platform.
     *
     * @return the type, not <code>null</code>
     */
    public String getType() {
        return type;
    }

    /**
     * Obtains a name that describes this platform.
     *
     * @return a message, not <code>null</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Obtains additional data that is platform specific.
     *
     * @return an object or <code>null</code> if no specific platform data exists
     */
    public Object getData() {
        return data;
    }

}
