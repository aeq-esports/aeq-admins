package de.esports.aeq.admins.members.domain;

import de.esports.aeq.admins.members.domain.exception.UnresolvableAccountIdException;

public final class AccountId {

    private String value;
    private Platform platform;

    public static AccountId of(String value, Platform platform) {
        return new AccountId(value, platform);
    }

    private AccountId(String value, Platform platform) {
        this.value = value;
        this.platform = platform;
    }

    /**
     * Obtains the actual value.
     * <p>
     * This value should be unique among the target platform.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Obtains the platform of this account.
     * <p>
     * If the platform is unknown, <code>null</code> will be returned instead. In this case, the
     * platform has to be resolved manually. If this process fails, an {@link
     * UnresolvableAccountIdException} should be thrown.
     *
     * @return the platform
     */
    public Platform getPlatform() {
        return platform;
    }
}
