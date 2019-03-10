package de.esports.aeq.admins.members.domain.account;

import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;

public interface Account extends Serializable {

    /**
     * Obtains the identifier of this account.
     * <p>
     * Generally, the identifier should be unique among all members.
     *
     * @return the identifier for this member
     */
    AccountId getAccountId();

    /**
     * Obtains the platform of this account.
     * <p>
     * This method exists for convenience and the default implementation returns the platform of the
     * account id.
     *
     * @return the platform of this account or <code>null</code> if no platform id is present
     * @see AccountId#getPlatform()
     */
    default Platform getPlatform() {
        return getAccountId().getPlatform();
    }

    /**
     * Obtains any object storing detailed information associated to this account.
     * <p>
     * The exact type of the underlying object can be determined using the type of the account id.
     *
     * @return an object or <code>null</code> if no additional information has been associated to
     * this account
     */
    Object getData();

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