package de.esports.aeq.admins.complaint.api;

import de.esports.aeq.admins.i18n.api.LocalizedMessage;
import de.esports.aeq.admins.platform.api.Platform;

import java.io.Serializable;
import java.util.Collection;

/**
 * Represents a predefined reason for a complaint.
 */
public class Reason implements Serializable {

    private Long id;
    private Collection<Platform> platforms;
    private LocalizedMessage message;
    private LocalizedMessage description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtains the platforms that this reason is valid for.
     * <p>
     * The returned collection is fully modifiable and changes the state of this object.
     *
     * @return a {@link Collection} of platforms, not <code>null</code>
     */
    public Collection<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Collection<Platform> platforms) {
        this.platforms = platforms;
    }

    public LocalizedMessage getMessage() {
        return message;
    }

    public void setMessage(LocalizedMessage message) {
        this.message = message;
    }

    public LocalizedMessage getDescription() {
        return description;
    }

    public void setDescription(LocalizedMessage description) {
        this.description = description;
    }
}
