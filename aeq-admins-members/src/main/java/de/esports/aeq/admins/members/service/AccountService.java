package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.members.domain.account.Account;
import de.esports.aeq.admins.members.domain.account.AccountId;

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

}
