package de.esports.aeq.admins.account.api;

import de.esports.aeq.admins.platform.api.Platform;

import java.time.Instant;
import java.util.Optional;

public interface Account {

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
        return accountId.getPlatform();
    }

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
     * The accuracy of that time should be handled with caution, as it is implementation dependent.
     * Some implementations may refresh the time regularly. while other preserve to do so only at a
     * given interval.
     * <p>
     * Please note that not all implementations support this feature.
     *
     * @return an {@link Optional} that either holds an {@link Instant} or is empty if this account
     * has no reported activity or this option is not supported
     */
    Optional<Instant> getLastSeenAt();

    /**
     * Returns whether this account is banned.
     *
     * @return <code>true</code> if this account is banned, otherwise <code>false</code>
     */
    boolean isBanned();

}