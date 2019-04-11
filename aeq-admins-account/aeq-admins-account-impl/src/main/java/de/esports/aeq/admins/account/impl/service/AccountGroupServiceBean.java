package de.esports.aeq.admins.account.impl.service;

import de.esports.aeq.account.api.AccountGroup;
import de.esports.aeq.account.api.service.AccountGroupService;
import org.springframework.stereotype.Service;

@Service
public class AccountGroupServiceBean implements AccountGroupService {

    @Override
    public AccountGroup getAccounts(Long groupId) {
        return null;
    }

    @Override
    public AccountGroup getAccountsByPlatform(Long groupId, Long platformId) {
        return null;
    }
}
