package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.members.AccountType;
import de.esports.aeq.admins.members.domain.account.Account;
import de.esports.aeq.admins.members.domain.account.AccountId;
import de.esports.aeq.admins.members.domain.account.AccountImpl;
import de.esports.aeq.admins.members.domain.account.Platform;
import de.esports.aeq.admins.members.domain.exception.AccountIdAlreadyOccupiedException;
import de.esports.aeq.admins.members.jpa.AccountRepository;
import de.esports.aeq.admins.members.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.members.jpa.entity.AccountTa;
import de.esports.aeq.admins.members.jpa.entity.PlatformTa;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceBean implements AccountService {

    private final ModelMapper mapper;
    private final AccountRepository accountRepository;
    private final PlatformService platformService;

    public AccountServiceBean(ModelMapper mapper, AccountRepository accountRepository,
            PlatformService platformService) {
        this.mapper = mapper;
        this.accountRepository = accountRepository;
        this.platformService = platformService;
    }

    //-----------------------------------------------------------------------

    @PostConstruct
    private void setup() {
        // setup mapper
        mapper.addConverter(this::mapAccountId, AccountId.class, AccountIdTa.class);
        mapper.addConverter(this::mapAccountIdTa, AccountIdTa.class, AccountId.class);
    }

    //-----------------------------------------------------------------------

    /*
     * Converters used by the mapper.
     */

    private AccountIdTa mapAccountId(MappingContext<AccountId, AccountIdTa> context) {
        AccountId source = context.getSource();

        AccountIdTa destination = new AccountIdTa();
        destination.setValue(source.getValue());
        destination.setType(source.getType());

        Platform platform = source.getPlatform();
        if (platform != null) {
            PlatformTa platformTa = mapper.map(platform, PlatformTa.class);
            destination.setPlatform(platformTa);
        }

        return destination;
    }

    private AccountId mapAccountIdTa(MappingContext<AccountIdTa, AccountId> context) {
        AccountIdTa source = context.getSource();

        AccountId destination = new AccountId(source.getValue(), source.getType());

        PlatformTa platformTa = source.getPlatform();
        if (platformTa != null) {
            Platform platform = mapper.map(platformTa, Platform.class);
            destination.setPlatform(platform);
        }

        return destination;
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
        return accountRepository.findById(accountId).map(this::toAccount)
                .orElseThrow(() -> new EntityNotFoundException(accountId));
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
        // if the account id is empty, create one
        if (account.getAccountId() == null) {
            account.setAccountId(AccountId.create(AccountType.TEMPORARY));
        }

        AccountId accountId = account.getAccountId();

        assertAccountIdNotAlreadyUsed(accountId);

        AccountTa entity = toAccountTa(account);
        entity.setCreatedAt(Instant.now());

        Platform platform = account.getPlatform();
        // assert the platform exists if present
        if (platform != null) {
            platformService.getPlatformById(platform.getId());
        }

        accountRepository.save(entity);

        return toAccount(entity);
    }

    //-----------------------------------------------------------------------

    @Override
    public void deleteAccount(AccountId accountId) {
        accountRepository.deleteById(accountId);
    }


    //-----------------------------------------------------------------------

    /*
     * Assertion methods.
     */

    private void assertAccountIdNotAlreadyUsed(AccountId accountId) {
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
}
