package de.esports.aeq.admins.account.api;

import de.esports.aeq.admins.platform.api.Platform;
import de.esports.aeq.admins.platform.api.PlatformInstance;
import de.esports.aeq.admins.platform.api.Platforms;

import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * Allows to identity an account.
 * <p>
 * More specifically, an account id is a symbolic link to an account. There are absolutely no
 * assumptions made about the existence or validity of the provided data. The provided values may or
 * may not actually exist.
 * <p>
 * In addition, the combination of <i>value</i>, <i>type</i> and <i>platform reference</i> should
 * also exist only once to be able to correctly identity an account.
 *
 * @see Account
 */
public final class AccountId {

    private String value;
    private String valueType;

    private Platform platform;
    private PlatformInstance platformInstance;

    public static AccountId create(Enum<AccountType> type) {
        return create(type.toString());
    }

    /**
     * Creates a new account id with a randomly generated unique id as value.
     *
     * @param type the type of the account id, not <code>null</code>
     * @return the created account id, not <code>null</code>
     */
    private static AccountId create(String type) {
        String value = UUID.randomUUID().toString();
        return new AccountId(value, type, Platforms.SYSTEM);
    }

    /**
     * Creates a new account id.
     *
     * @param value     the actual account id value, not <code>null</code>
     * @param valueType the type of the <code>value</code>, not <code>null</code>
     * @param platform  the platform, not <code>null</code>
     */
    public AccountId(String value, String valueType, Platform platform) {
        this.value = requireNonNull(value);
        this.valueType = requireNonNull(valueType);
        this.platform = requireNonNull(platform);
    }

    /**
     * Obtains the actual value.
     * <p>
     * This value can represent anything that can be used to identity an account, such as a unique
     * id or a nickname.
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
     * Obtains the account value type.
     * <p>
     * The account type specifies the exact type of the <code>value</code> as there are often
     * multiple ways to identify one account on one platform.
     *
     * @return the account type, not <code>null</code>
     */
    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }


    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    /**
     * Obtains the concrete platform instance that this account id is valid on.
     * <p>
     * This data may be used to distinguish multiple concrete platforms of the same type, for
     * example multiple servers. If the platform can be identified without the need of additional
     * information, this method can return <code>null</code>.
     *
     * @return an object or <code>null</code> if none is present
     */
    public Optional<PlatformInstance> getPlatformInstance() {
        return Optional.ofNullable(platformInstance);
    }

    public void setPlatformInstance(PlatformInstance platformInstance) {
        this.platformInstance = platformInstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountId)) return false;
        AccountId accountId = (AccountId) o;
        return Objects.equals(value, accountId.value) &&
                Objects.equals(valueType, accountId.valueType) &&
                Objects.equals(platform, accountId.platform) &&
                Objects.equals(platformInstance, accountId.platformInstance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, valueType, platform, platformInstance);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccountId.class.getSimpleName() + "[", "]")
                .add("value='" + value + "'")
                .add("type='" + valueType + "'")
                .add("platform=" + platform)
                .add("platformInstance=" + platformInstance)
                .toString();
    }

}
