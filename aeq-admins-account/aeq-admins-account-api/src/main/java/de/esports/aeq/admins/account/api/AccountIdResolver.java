package de.esports.aeq.admins.account.api;

import de.esports.aeq.admins.account.api.exception.UnresolvableAccountException;

public interface AccountIdResolver {

    void resolveAccountId(Account account) throws UnresolvableAccountException;
}
