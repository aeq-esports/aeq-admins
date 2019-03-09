package de.esports.aeq.admins.members.domain;

import de.esports.aeq.admins.members.domain.account.Account;
import de.esports.aeq.admins.members.domain.account.AccountImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static java.util.Objects.requireNonNull;

public class Member extends AccountImpl {

    private Collection<Account> accounts = new HashSet<>();

    public void addAccount(Account account) {
        accounts.add(requireNonNull(account));
    }

    public Collection<Account> getAccounts() {
        return Collections.unmodifiableCollection(accounts);
    }
}
