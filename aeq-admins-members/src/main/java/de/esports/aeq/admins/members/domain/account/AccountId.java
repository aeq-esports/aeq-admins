package de.esports.aeq.admins.members.domain.account;

import de.esports.aeq.admins.members.AccountType;
import de.esports.aeq.admins.platform.api.PlatformReference;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

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
public final class AccountId implements Serializable {

    private String value;
    private String type;
    private PlatformReference platformReference;

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
        return new AccountId(value, type);
    }

    public AccountId(String value, String type) {
        this.value = Objects.requireNonNull(value);
        this.type = Objects.requireNonNull(type);
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
     * Obtains the platform reference that this account id is valid on.
     *
     * @return the platform reference, not <code>null</code>
     */
    public PlatformReference getPlatformReference() {
        return platformReference;
    }

    public void setPlatformReference(PlatformReference platformReference) {
        this.platformReference = platformReference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountId)) return false;
        AccountId accountId = (AccountId) o;
        return value.equals(accountId.value) && type.equals(accountId.type) &&
                platformReference.equals(accountId.platformReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type, platformReference);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccountId.class.getSimpleName() + "[", "]")
                .add("value='" + value + "'")
                .add("type='" + type + "'")
                .add("platformReference=" + platformReference)
                .toString();
    }
}
