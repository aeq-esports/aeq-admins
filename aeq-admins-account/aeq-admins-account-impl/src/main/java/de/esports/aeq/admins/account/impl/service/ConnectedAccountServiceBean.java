package de.esports.aeq.admins.account.impl.service;

import de.esports.aeq.admins.account.api.AccountId;
import de.esports.aeq.admins.account.api.AccountMapper;
import de.esports.aeq.admins.account.api.ConnectedAccount;
import de.esports.aeq.admins.account.api.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.account.api.service.ConnectedAccountService;
import de.esports.aeq.admins.account.impl.jpa.ConnectedAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class ConnectedAccountServiceBean implements ConnectedAccountService {

    private AccountMapper accountMapper;
    private final ConnectedAccountRepository repository;

    @Autowired
    public ConnectedAccountServiceBean(AccountMapper accountMapper,
            ConnectedAccountRepository repository) {
        this.accountMapper = accountMapper;
        this.repository = repository;
    }

    @Override
    public Optional<Long> getUserIdByConnectedAccountId(AccountId accountId) {
        return Optional.empty();
    }

    @Override
    public boolean isAlreadyConnected(Set<ConnectedAccount> accounts) {
        requireNonNull(accounts);
        Set<AccountIdTa> connectedAccounts = accounts.stream()
                .map(ConnectedAccount::getAccountId)
                .map(accountMapper::toAccountIdTa)
                .collect(Collectors.toSet());
        return !repository.findAllByAccountIdIn(connectedAccounts).isEmpty();
    }
}
