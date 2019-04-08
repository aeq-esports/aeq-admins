package de.esports.aeq.admins.members.domain;

import de.esports.aeq.account.api.Account;
import de.esports.aeq.account.api.AccountId;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static java.util.Objects.requireNonNull;

public class Member implements Account {

    private Long id;

    private AccountId accountId;
    private MemberData data;
    private Instant createdAt;
    private Instant lastSeenAt;

    private Collection<Account> accounts = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public AccountId getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(AccountId accountId) {
        this.accountId = requireNonNull(accountId);
    }

    @Override
    public MemberData getData() {
        return data;
    }

    public void setData(MemberData data) {
        this.data = requireNonNull(data);
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

    public void addAccount(Account account) {
        accounts.add(requireNonNull(account));
    }

    public Collection<Account> getAccounts() {
        return Collections.unmodifiableCollection(accounts);
    }
}
