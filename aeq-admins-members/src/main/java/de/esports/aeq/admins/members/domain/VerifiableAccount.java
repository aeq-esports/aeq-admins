package de.esports.aeq.admins.members.domain;

import java.time.Instant;

public interface VerifiableAccount extends Account {

    /**
     * Returns whether this account has been verified.
     *
     * @return <code>true</code> if this account has been verified, otherwise <code>false</code>
     */
    boolean isVerified();

    /**
     * Obtains the exact time this account has been verified.
     *
     * @return an {@link Instant} representing the time this account has been verified or
     * <code>null</code> if this account has not been verified yet
     */
    Instant getVerifiedAt();
}
