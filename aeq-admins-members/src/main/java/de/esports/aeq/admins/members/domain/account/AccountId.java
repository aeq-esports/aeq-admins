package de.esports.aeq.admins.members.domain.account;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Allows to identity an account.
 * <p>
 * Please note that there should be absolutely made no assumptions about the validity of the values
 * representing the account id. The provided values may or may not actually exist.
 *
 * @see Account
 */
public final class AccountId implements Serializable {

    private String value;
    private String type;
    private Platform platform;

    public AccountId(String value, String type) {
        this.value = Objects.requireNonNull(value);
        this.type = Objects.requireNonNull(type);
    }

    /**
     * Obtains the actual value.
     * <p>
     * This value can represent anything that can be used to identity an account, such as a unique
     * id or a nickname. The <code>value</code> of this account id must be unique, also taking the
     * <code>type</code> and <code>platform</code> into account, to be able to correctly identity
     * an account it belongs to.
     *
     * @return the value, not <code>null</code>
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
     * The account type specifies the exact type of the <code>value</code> as there are often
     * multiple ways to identify one account on one platform.
     *
     * @return the account type, not <code>null</code>
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtains the platform that this account id is valid on.
     * <p>
     * If this account can be identified without the need of a platform, this method may return
     * <code>null</code>. This can be the case is the combination of <code>value</code> and
     * <code>type</code> is already unique on all platforms.
     *
     * @return the platform or <code>null</code> if no platform id is present
     */
    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountId)) return false;
        AccountId accountId = (AccountId) o;
        return value.equals(accountId.value) && type.equals(accountId.type) &&
                platform.equals(accountId.platform);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type, platform);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccountId.class.getSimpleName() + "[", "]")
                .add("value='" + value + "'")
                .add("type='" + type + "'")
                .add("platform=" + platform)
                .toString();
    }
}
