package de.esports.aeq.admins.account.api.service;

import de.esports.aeq.admins.account.api.Account;
import de.esports.aeq.admins.account.api.AccountId;
import de.esports.aeq.admins.account.api.AccountType;

import java.time.Instant;
import java.util.Collection;

public interface AccountService {

    /**
     * Obtains all accounts.
     *
     * @return a modifiable {@link Collection} of accounts, not <code>null</code>
     */
    Collection<Account> getAccounts();

    /**
     * Obtains all accounts with recent activity after a defined threshold.
     * <p>
     * Account types which not supporting this feature (<code>lastSeenAt</code> equivalent to
     * <code>null</code>) will be excluded from the result.
     *
     * @param lastSeenAt an {@link Instant} representing the threshold
     * @return a {@link Collection} of accounts that match the threshold
     */
    Collection<Account> getAccounts(Instant lastSeenAt);

    /**
     * Obtains all accounts that match the given <code>accountId</code>.
     *
     * @param accountId the account id
     * @return a modifiable {@link Collection} of accounts, not <code>null</code>
     */
    Account getAccountById(AccountId accountId);

    Collection<Account> getAccountsByType(String type);

    default Collection<Account> getAccountsByType(Enum<? extends AccountType> type) {
        return getAccountsByType(type.toString());
    }

    Collection<Account> getAccountsByType(String type, Instant lastSeenAt);

    Account createAccount(Account account);

    void deleteAccount(AccountId accountId);

}
