package de.esports.aeq.account.api;

import de.esports.aeq.account.api.exception.UnresolvableAccountException;

public interface AccountIdResolver {

    void resolveAccountId(Account account) throws UnresolvableAccountException;
}
