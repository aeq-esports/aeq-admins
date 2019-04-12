package de.esports.aeq.admins.account.api;

import java.time.Instant;
import java.util.Optional;

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
     * Obtains the time this account has been verified.
     * <p>
     * Please note that not all implementations support this feature.
     *
     * @return an {@link Optional} holding the verification time or is empty if this account has not
     * been verified yet or this feature is not supported
     * @see #isVerified()
     */
    Optional<Instant> getVerifiedAt();

    /**
     * Returns whether this account is verified.
     * <p>
     * This method also validates if the point of verification is in the past.
     *
     * @return <code>true</code> if this account id verified, otherwise <code>false</code>
     */
    default boolean isVerified() {
        return getVerifiedAt().filter(e -> e.isBefore(Instant.now())).map(e -> Boolean.TRUE)
                .orElse(Boolean.FALSE);
    }

}
