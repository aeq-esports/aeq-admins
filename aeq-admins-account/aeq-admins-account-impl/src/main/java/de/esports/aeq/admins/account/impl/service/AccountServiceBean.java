package de.esports.aeq.admins.account.impl.service;

import de.esports.aeq.admins.account.api.Account;
import de.esports.aeq.admins.account.api.AccountId;
import de.esports.aeq.admins.account.api.AccountType;
import de.esports.aeq.admins.account.api.exception.AccountIdAlreadyOccupiedException;
import de.esports.aeq.admins.account.api.service.AccountService;
import de.esports.aeq.admins.account.impl.AccountMapper;
import de.esports.aeq.admins.account.impl.jpa.AccountRepository;
import de.esports.aeq.admins.account.api.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.account.api.jpa.entity.AccountTa;
import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.platform.api.Platform;
import de.esports.aeq.admins.platform.api.service.PlatformService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AccountServiceBean implements AccountService {

    private final AccountMapper mapper;
    private final AccountRepository accountRepository;
    private final PlatformService platformService;

    public AccountServiceBean(AccountMapper mapper, AccountRepository accountRepository,
            PlatformService platformService) {
        this.mapper = mapper;
        this.accountRepository = accountRepository;
        this.platformService = platformService;
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<Account> getAccounts() {
        return accountRepository.findAll().stream().map(mapper::toAccount)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Account> getAccounts(Instant lastSeenAt) {
        return accountRepository.findAllByLastSeenAfter(lastSeenAt).stream().map(mapper::toAccount)
                .collect(Collectors.toList());
    }

    @Override
    public Account getAccountById(AccountId accountId) {
        AccountIdTa accountIdTa = mapper.toAccountIdTa(accountId);
        return accountRepository.findAllByAccountId(accountIdTa).stream()
                .findFirst().map(mapper::toAccount)
                .orElseThrow(() -> new EntityNotFoundException(accountId));
    }

    @Override
    public Collection<Account> getAccountsByType(String type) {
        return accountRepository.findAllByAccountIdType(type).stream().map(mapper::toAccount)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Account> getAccountsByType(String type, Instant lastSeenAt) {
        return accountRepository.findAllByAccountIdTypeAndLastSeenAfter(type, lastSeenAt).stream()
                .map(mapper::toAccount).collect(Collectors.toList());
    }

    //-----------------------------------------------------------------------

    @Override
    public Account createAccount(Account account) {
        // if the account id is empty, create one
        if (account.getAccountId() == null) {
            account.setAccountId(AccountId.create(AccountType.TEMPORARY));
        }

        AccountId accountId = account.getAccountId();

        assertAccountIdNotAlreadyUsed(accountId);

        AccountTa entity = mapper.toAccountTa(account);
        entity.setCreatedAt(Instant.now());

        Platform platform = account.getPlatform();
        // assert the platform exists if present
        if (platform != null) {
            platformService.getPlatformById(platform.getId());
        }

        accountRepository.save(entity);

        return mapper.toAccount(entity);
    }

    //-----------------------------------------------------------------------

    @Override
    public void deleteAccount(AccountId accountId) {
        AccountIdTa accountIdTa = mapper.toAccountIdTa(accountId);
        accountRepository.deleteByAccountId(accountIdTa);
    }

    //-----------------------------------------------------------------------

    /*
     * Assertion methods.
     */

    private void assertAccountIdNotAlreadyUsed(AccountId accountId) {
        AccountIdTa accountIdTa = mapper.toAccountIdTa(accountId);
        accountRepository.findAllByAccountId(accountIdTa).stream().findAny().ifPresent(e -> {
            throw new AccountIdAlreadyOccupiedException(accountId);
        });
    }

}
