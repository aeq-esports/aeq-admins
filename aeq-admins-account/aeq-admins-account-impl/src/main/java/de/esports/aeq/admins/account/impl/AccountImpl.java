package de.esports.aeq.admins.account.impl;

import de.esports.aeq.admins.account.api.AccountId;
import de.esports.aeq.admins.account.api.VerifiableAccount;

import java.time.Instant;
import java.util.Optional;

public class AccountImpl implements VerifiableAccount {

    private AccountId accountId;
    private Instant createdAt;
    private Instant lastSeenAt;
    private Instant verifiedAt;
    private boolean isBanned;

    @Override
    public AccountId getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Optional<Instant> getLastSeenAt() {
        return Optional.ofNullable(lastSeenAt);
    }

    public void setLastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }

    @Override
    public Optional<Instant> getVerifiedAt() {
        return Optional.ofNullable(verifiedAt);
    }

    public void setVerifiedAt(Instant verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    @Override
    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
