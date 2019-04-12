package de.esports.aeq.admins.member.api;

import de.esports.aeq.admins.account.api.Account;
import de.esports.aeq.admins.account.api.AccountId;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Represents a member.
 */
public class Member implements Account {

    private AccountId accountId;
    private MemberDetails data;
    private Instant createdAt;
    private Instant lastSeenAt;
    private boolean isBanned;

    private Collection<Account> accounts = new HashSet<>();

    /**
     * Obtains the account id that represents this member.
     *
     * @return the account id, never <code>null</code>
     */
    public AccountId getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountId accountId) {
        this.accountId = requireNonNull(accountId);
    }

    /**
     * Obtains additional details, mostly personal information about this member.
     *
     * @return the details, not <code>null</code>
     */
    public MemberDetails getData() {
        return data;
    }

    public void setData(MemberDetails data) {
        this.data = requireNonNull(data);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Optional<Instant> getLastSeenAt() {
        return Optional.ofNullable(lastSeenAt);
    }

    public void setLastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }

    @Override
    public boolean isBanned() {
        return isBanned;
    }

    /**
     * Obtains an <i>unmodifiable</i> view of all accounts that belong to this user.
     *
     * @return
     */
    public Collection<Account> getAccounts() {
        return Collections.unmodifiableCollection(accounts);
    }

    public void addAccount(Account account) {
        accounts.add(requireNonNull(account));
    }
}
