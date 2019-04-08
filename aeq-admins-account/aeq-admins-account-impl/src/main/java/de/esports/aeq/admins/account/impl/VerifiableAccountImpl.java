package de.esports.aeq.admins.account.impl;

import java.time.Instant;

public class VerifiableAccountImpl extends AccountImpl {

    private boolean verified;
    private Instant verifiedAt;

    /**
     * Returns whether this account has been verified.
     *
     * @return <code>true</code> if this account has been verified, otherwise <code>false</code>
     */
    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    /**
     * Obtains the exact time this account has been verified.
     *
     * @return an {@link Instant} representing the time this account has been verified or
     * <code>null</code> if this account has not been verified yet
     */
    public Instant getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Instant verifiedAt) {
        this.verifiedAt = verifiedAt;
    }
}
