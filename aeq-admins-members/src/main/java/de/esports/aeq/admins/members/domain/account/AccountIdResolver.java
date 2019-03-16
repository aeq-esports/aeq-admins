package de.esports.aeq.admins.members.domain.account;

import de.esports.aeq.admins.members.domain.exception.UnresolvableAccountException;

public interface AccountIdResolver {

    void resolveAccountId(Account account) throws UnresolvableAccountException;
}
