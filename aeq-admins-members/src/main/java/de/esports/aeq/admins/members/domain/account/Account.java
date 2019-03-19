package de.esports.aeq.admins.members.domain.account;

import de.esports.aeq.admins.platform.api.Platform;

import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;

public interface Account extends Serializable {

    /**
     * Obtains the identifier of this account.
     * <p>
     * Generally, the account id should be unique among all accounts.
     *
     * @return the account id or <code>null</code> if this account id has not been resolved yet
     */
    AccountId getAccountId();

    /**
     * Sets the account id.
     *
     * @param accountId the account id, may be <code>null</code>
     */
    void setAccountId(AccountId accountId);

    /**
     * Obtains the platform of this account.
     * <p>
     * This method exists for convenience and the default implementation returns the platform of the
     * referenced platform of the account id.
     *
     * @return the platform of this account or <code>null</code> none id present
     */
    default Platform getPlatform() {
        AccountId accountId = getAccountId();
        if (accountId == null) {
            return null;
        }
        return accountId.getPlatformReference().getPlatform();
    }

    /**
     * Obtains an object storing detailed information associated to this account.
     * <p>
     * The exact type of the object can be determined using the type of the account id. Please note
     * that the underlying object might be <code>null</code>, although additional information is
     * available, but has not been resolved for performance reasons.
     *
     * @return an object or <code>null</code> if no additional information is available
     */
    Object getData();

    void setData(Object data);

    /**
     * Obtains the exact time this account has been created at.
     *
     * @return an {@link Instant} that represents the time this account has been created at, never
     * <code>null</code>
     */
    Instant getCreatedAt();

    /**
     * Obtains the time the last action that has been performed on this account.
     * <p>
     * Please note that not all implementations support this feature, represented through an empty
     * {@link Optional}. Also, the accuracy of this time is not guaranteed.
     *
     * @return an {@link Optional} that either holds an {@link Instant} or is empty if this option
     * is not supported for this account
     */
    Instant getLastSeenAt();
}