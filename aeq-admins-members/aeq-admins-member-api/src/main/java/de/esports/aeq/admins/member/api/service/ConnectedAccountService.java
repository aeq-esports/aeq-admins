package de.esports.aeq.admins.member.api.service;

import de.esports.aeq.admins.account.api.AccountId;
import de.esports.aeq.admins.member.api.ConnectedAccount;
import de.esports.aeq.admins.member.api.Member;

import java.util.Optional;
import java.util.Set;

public interface ConnectedAccountService {

    /**
     * Obtains a member that has a connected account matching the given <code>accountId</code>.
     *
     * @param accountId the account id, not <code>null</code>
     * @return an {@link Optional} holding the member or is empty if no result is present
     */
    Optional<Member> getMemberByConnectedAccountId(AccountId accountId);

    boolean isAlreadyConnected(Set<ConnectedAccount> accounts);
}
