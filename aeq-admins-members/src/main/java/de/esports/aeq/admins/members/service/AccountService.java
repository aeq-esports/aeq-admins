package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.members.PlatformType;
import de.esports.aeq.admins.members.domain.Account;
import de.esports.aeq.admins.members.domain.AccountId;
import de.esports.aeq.admins.members.domain.exception.UnresolvableAccountIdException;

import java.time.Instant;
import java.util.Collection;

public interface AccountService {

    Collection<Account> getAccounts();

    Collection<Account> getAccounts(Instant lastSeenAt);

    Account getAccountById(AccountId accountId);

    Collection<Account> getAccountsByType(String type);

    Collection<Account> getAccountsByType(String type, Instant lastSeenAt);

    Account createAccount(Account account);

    void deleteAccount(AccountId accountId);

    //-----------------------------------------------------------------------

    void resolve(AccountId accountId) throws UnresolvableAccountIdException;

    void resolveTo(AccountId accountId, String platform) throws UnresolvableAccountIdException;

    default void resolveTo(AccountId accountId, PlatformType platformType) throws UnresolvableAccountIdException {
        resolveTo(accountId, platformType.toString());
    }
}
