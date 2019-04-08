package de.esports.aeq.account.api;

import java.time.Instant;

/**
 * Represents an account which can be verified by the account holder.
 * <p>
 * A verified account signals that the identity of this account has been verified by the account
 * holder and thus can be trusted. This is important for security related operations which require
 * the real account holder to carry out the intended action. In contrary, an account which is not
 * verified signals that the identity of this account should not be trusted, since the account might
 * not belong the actual account holder.
 * <p>
 * The verification process should be delegated to an {@link AccountVerifier}, which updates the
 * verification status of this account.
 */
public interface VerifiableAccount extends Account {

    /**
     * Obtains the verification status.
     *
     * @return <code>true</code> if this account has been verified, otherwise <code>false</code>
     */
    boolean isVerified();

    /**
     * Obtains the exact time this account has been verified on.
     * <p>
     * The return value of this method should depend on {@link #isVerified()}. If verified, the
     * return value should always be different from <code>null</code>. On the other hand, if not
     * verified, the return value should always be <code>null</code>.
     *
     * @return an {@link Instant} representing the time this account has been verified on
     */
    Instant getVerifiedAt();
}
