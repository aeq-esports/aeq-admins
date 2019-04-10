package de.esports.aeq.admins.member.api;

import de.esports.aeq.account.api.Account;
import de.esports.aeq.account.api.AccountId;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static java.util.Objects.requireNonNull;

/**
 * Represents a member.
 */
public class Member {

    private Long id;
    private AccountId accountId;

    private MemberDetails data;
    private Instant createdAt;
    private Instant lastSeenAt;

    private Collection<Account> accounts = new HashSet<>();

    /**
     * Obtains the id of the member.
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtains the account id that represents this member.
     * <p>
     * While the value obtained by {@link #getId()} may vary between systems, the account id is
     * meant to be unique among multiple systems.
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

    public Instant getLastSeenAt() {
        return lastSeenAt;
    }

    public void setLastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
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
