package de.esports.aeq.admins.platform.api;

/**
 * Represents data used to identify a concrete platform instance.
 * <p>
 * When there can exist multiple instances of one platforms, each platform must be able to be
 * identified with the provided platform data. This can, for example, be a region or an ip-address.
 */
public interface PlatformInstance {

    Long getId();
}
