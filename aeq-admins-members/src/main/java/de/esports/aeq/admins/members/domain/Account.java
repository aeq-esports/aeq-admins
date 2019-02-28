package de.esports.aeq.admins.members.domain;

import java.time.Instant;
import java.util.Optional;

public class Account {

    private AccountId accountId;
    private Instant createdAt;
    private Instant lastSeenAt;

    /**
     * Obtains the identifier of this account.
     * <p>
     * Generally, the identifier should be unique among all members.
     *
     * @return the identifier for this member
     */
    public AccountId getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }

    /**
     * Obtains the exact time this account has been created at.
     *
     * @return an {@link Instant} that represents the time this account has been created at, never
     * <code>null</code>
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Obtains the time the last action that has been performed on this account.
     * <p>
     * Please note that not all implementations support this feature, represented through an empty
     * {@link Optional}. Also, the accuracy of this time is not guaranteed.
     *
     * @return an {@link Optional} that either holds an {@link Instant} or is empty if this option
     * is not supported for this account
     */
    public Instant getLastSeenAt() {
        return lastSeenAt;
    }

    public void setLastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }
}
