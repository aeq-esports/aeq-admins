package de.esports.aeq.admins.account.api.service;

import de.esports.aeq.admins.account.api.AccountId;
import de.esports.aeq.admins.account.api.ConnectedAccount;

import java.util.Optional;
import java.util.Set;

public interface ConnectedAccountService {

    /**
     * Obtains a user id that has a connected account matching the given <code>accountId</code>.
     *
     * @param accountId the account id, not <code>null</code>
     * @return an {@link Optional} holding the member or is empty if no result is present
     */
    Optional<Long> getUserIdByConnectedAccountId(AccountId accountId);

    boolean isAlreadyConnected(Set<ConnectedAccount> accounts);
}
