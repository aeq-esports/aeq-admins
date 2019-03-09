package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.members.domain.account.*;
import de.esports.aeq.admins.members.domain.exception.AccountIdAlreadyOccupiedException;
import de.esports.aeq.admins.members.jpa.AccountPropertyDataRepository;
import de.esports.aeq.admins.members.jpa.AccountRepository;
import de.esports.aeq.admins.members.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.members.jpa.entity.AccountPropertyDataTa;
import de.esports.aeq.admins.members.jpa.entity.AccountTa;
import de.esports.aeq.admins.members.jpa.entity.PlatformTa;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceBean implements AccountService {

    private final ModelMapper mapper;
    private final AccountRepository accountRepository;
    private final AccountPropertyDataRepository propertyDataRepository;
    private final PlatformService platformService;

    public AccountServiceBean(ModelMapper mapper, AccountRepository accountRepository,
            AccountPropertyDataRepository propertyDataRepository,
            PlatformService platformService) {
        this.mapper = mapper;
        this.accountRepository = accountRepository;
        this.propertyDataRepository = propertyDataRepository;
        this.platformService = platformService;
    }

    //-----------------------------------------------------------------------

    @PostConstruct
    private void setupMapper() {
        Converter<AccountId, AccountIdTa> mapAccountId = c -> {
            AccountId accountId = c.getSource();
            return new AccountIdTa(accountId.getValue(), accountId.getType());
        };

        Converter<AccountIdTa, AccountId> mapAccountIdTa = c -> {
            AccountIdTa accountId = c.getSource();
            AccountId result = new AccountId(accountId.getValue(), accountId.getType());
            PlatformTa platform = accountId.getPlatform();

            // TODO

            result.setPlatform();
        };

        mapper.addConverter(mapAccountId, AccountId.class, AccountIdTa.class);
        mapper.addConverter(mapAccountIdTa, AccountIdTa.class, AccountId.class);
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<Account> getAccounts() {
        return accountRepository.findAll().stream().map(this::toAccount)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Account> getAccounts(Instant lastSeenAt) {
        return accountRepository.findAllByLastSeenAfter(lastSeenAt).stream().map(this::toAccount)
                .collect(Collectors.toList());
    }

    @Override
    public Account getAccountById(AccountId accountId) {
        AccountTa entity = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(accountId));

        AccountImpl account = toAccount(entity);

        // TODO: restore platform info as well

        Class<?> dataType = entity.getDataType();
        if (dataType != null) {
            Object accountData = getAccountData(accountId, dataType);
            account.setData(accountData);
        }

        return account;
    }

    @Override
    public Collection<Account> getAccountsByType(String type) {
        return accountRepository.findAllByAccountIdType(type).stream().map(this::toAccount)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Account> getAccountsByType(String type, Instant lastSeenAt) {
        return accountRepository.findAllByAccountIdTypeAndLastSeenAfter(type, lastSeenAt).stream()
                .map(this::toAccount).collect(Collectors.toList());
    }

    //-----------------------------------------------------------------------

    @Override
    public Account createAccount(Account account) {
        AccountId accountId = account.getAccountId();
        checkAccountIdAlreadyOccupied(accountId);

        AccountTa entity = toAccountTa(account);
        entity.setCreatedAt(Instant.now());

        Platform platform = account.getPlatform();
        // assert the platform id exists
        if (platform != null) {
            platformService.getPlatformById(platform.getId());
        }

        accountRepository.save(entity);

        Object accountData = account.getData();
        if (accountData != null) {
            persistAccountData(accountId, accountData);
        }

        return toAccount(entity);
    }

    private void persistAccountData(AccountId accountId, Object accountData) {
        Class<?> dataClass = accountData.getClass();

        if (accountData instanceof Serializable) {
            throw new RuntimeException(dataClass + " must be serializable.");
        }

        if (accountData instanceof SimplePropertyMap) {
            persistPropertyAccountData(accountId, (SimplePropertyMap) accountData);
            return;
        }

        throw new RuntimeException("No entity processing method found for class " + dataClass);
    }

    private void persistPropertyAccountData(AccountId accountId, SimplePropertyMap properties) {
        AccountIdTa accountIdTa = toAccountIdTa(accountId);

        AccountPropertyDataTa entity = new AccountPropertyDataTa();
        entity.setAccountId(accountIdTa);
        entity.setProperties(properties.getProperties());

        propertyDataRepository.save(entity);
    }

    private Object getAccountData(AccountId accountId, Class<?> dataClass) {
        if (SimplePropertyMap.class.isAssignableFrom(dataClass)) {
            return getPropertyAccountData(accountId);
        }

        throw new RuntimeException("No entity processing method found for class " + dataClass);
    }

    private SimplePropertyMap getPropertyAccountData(AccountId accountId) {
        AccountIdTa accountIdTa = toAccountIdTa(accountId);

        var entityOpt = propertyDataRepository.findById(accountIdTa);
        if (entityOpt.isEmpty()) {
            return new PropertyPlatformData();
        }

        AccountPropertyDataTa entity = entityOpt.get();
        return new PropertyPlatformData(entity.getProperties());
    }

    @Override
    public void deleteAccount(AccountId accountId) {
        accountRepository.deleteById(accountId);
    }


    //-----------------------------------------------------------------------

    /*
     * Assertion methods.
     */

    private void checkAccountIdAlreadyOccupied(AccountId accountId) {
        Optional<AccountTa> existing = accountRepository.findById(accountId);
        if (existing.isPresent()) {
            throw new AccountIdAlreadyOccupiedException(accountId);
        }
    }

    //-----------------------------------------------------------------------

    /*
     * Convenience methods to be used for mapping.
     */

    private AccountImpl toAccount(AccountTa account) {
        return mapper.map(account, AccountImpl.class);
    }

    private AccountTa toAccountTa(Account account) {
        return mapper.map(account, AccountTa.class);
    }

    private AccountIdTa toAccountIdTa(AccountId accountId) {
        return mapper.map(accountId, AccountIdTa.class);
    }
}
