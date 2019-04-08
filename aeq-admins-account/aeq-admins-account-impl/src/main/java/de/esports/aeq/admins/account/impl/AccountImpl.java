package de.esports.aeq.admins.account.impl;

import de.esports.aeq.account.api.Account;
import de.esports.aeq.account.api.AccountId;

import java.time.Instant;

public class AccountImpl implements Account {

    private AccountId accountId;
    private Object data;
    private Instant createdAt;
    private Instant lastSeenAt;

    @Override
    public AccountId getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }

    @Override
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Instant getLastSeenAt() {
        return lastSeenAt;
    }

    public void setLastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }
}
