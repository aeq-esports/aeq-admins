package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.members.domain.account.Account;
import de.esports.aeq.admins.members.domain.account.AccountId;
import de.esports.aeq.admins.members.domain.account.VerifiableAccount;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

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
    Collection<Account> getAccountsById(AccountId accountId);

    Collection<Account> getAccountsByType(String type);

    Collection<Account> getAccountsByType(String type, Instant lastSeenAt);

    /**
     * Obtains a verified account that matches the target <code>accountId</code>.
     * <p>
     * Please note that there can only be one verified account per account id. If there should exist
     * multiple accounts with the same verified account id, the implementation might throw an {@link
     * IllegalStateException}. If no account matches the given account id or if there exist accounts
     * matching the account id but those accounts are not verified, an <i>empty</i> {@link Optional}
     * will be returned.
     *
     * @param accountId the account id, not <code>null</code>
     * @return an {@link Optional} holding the account or is <i>empty</i> if no account is present
     * that matches the criteria
     */
    Optional<VerifiableAccount> getVerifiedAccount(AccountId accountId);

    Account createAccount(Account account);

    void deleteAccount(AccountId accountId);

}
