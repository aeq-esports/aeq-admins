package de.esports.aeq.admins.platform.api;

import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

/**
 * Represents a reference to a specific platform.
 */
public final class PlatformReference {

    private Platform platform;
    private PlatformData data;

    public PlatformReference of(Platform platform) {
        return new PlatformReference(platform, null);
    }

    public PlatformReference of(PlatformData data) {
        requireNonNull(data);
        return new PlatformReference(data.getPlatform(), data);
    }

    private PlatformReference(Platform platform, PlatformData data) {
        this.platform = requireNonNull(platform);
        this.data = data;
    }

    /**
     * Obtains the platform type.
     *
     * @return the platform, not <code>null</code>
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Obtains additional data that is used to identity a concrete platform.
     * <p>
     * This data may be used to distinguish multiple concrete platforms of the same type, for
     * example multiple servers. If the platform can be identified without the need of additional
     * information, this method can return <code>null</code>.
     *
     * @return an object or <code>null</code> if none is present
     */
    public PlatformData getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlatformReference)) return false;
        PlatformReference that = (PlatformReference) o;
        return platform.equals(that.platform) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(platform, data);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PlatformReference.class.getSimpleName() + "[", "]")
                .add("platform=" + platform)
                .add("data=" + data)
                .toString();
    }
}
