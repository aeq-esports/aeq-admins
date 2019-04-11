package de.esports.aeq.account.api.service;

import de.esports.aeq.account.api.AccountGroup;

public interface AccountGroupService {

    /**
     * Obtains all accounts for a specific account group id.
     *
     * @param groupId the account group id, not <code>null</code>
     * @return an account group, not <code>null</code>
     */
    AccountGroup getAccounts(Long groupId);

    /**
     * Obtains all accounts for a specific account group id that match the provided platform id.
     * <p>
     * The resulting account group will only contain the filtered results.
     *
     * @param groupId    the account group id, not <code>null</code>
     * @param platformId the platform id, not <code>null</code>
     * @return the account group, not <code>null</code>
     */
    AccountGroup getAccountsByPlatform(Long groupId, Long platformId);
}
