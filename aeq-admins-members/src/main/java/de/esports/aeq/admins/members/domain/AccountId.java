package de.esports.aeq.admins.members.domain;

/**
 * Allows to identity the holder of an account.
 *
 * @see Account
 */
public final class AccountId {

    private String value;
    private String type;
    private Platform platform;

    /**
     * Obtains the actual value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Obtains the account type.
     * <p>
     * The account type specifies the exact type of the <code>value</code> on the target platform as
     * there are often multiple ways to identify one account. This can be, for example, a unique id
     * or a nickname.
     *
     * @return the account type, can be <code>null</code>
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtains the platform of this account.
     * <p>
     * The platform allows to identify this account id among multiple platforms or servers. If the
     * <code>type</code> is sufficient to uniquely identity account ids on multiple platforms or of
     * only one platform exists, the platform may be <code>null</code>.
     *
     * @return the platform
     */
    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}
