package de.esports.aeq.admins.account.impl;

import de.esports.aeq.account.api.Account;
import de.esports.aeq.account.api.AccountId;

import java.time.Instant;

public class AnonymousAccount implements Account {

    private AccountId accountId;
    private Object data;

    public static AnonymousAccount of(AccountId accountId, Object data) {
        return new AnonymousAccount(accountId, data);
    }

    public AnonymousAccount() {

    }

    private AnonymousAccount(AccountId accountId, Object data) {
        this.accountId = accountId;
        this.data = data;
    }

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
        return null;
    }

    @Override
    public Instant getLastSeenAt() {
        return null;
    }
}
