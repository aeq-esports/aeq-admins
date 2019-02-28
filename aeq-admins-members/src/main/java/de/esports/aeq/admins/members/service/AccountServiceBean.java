package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.members.domain.Account;
import de.esports.aeq.admins.members.domain.AccountId;
import de.esports.aeq.admins.members.domain.Complaint;
import de.esports.aeq.admins.members.jpa.AccountRepository;
import de.esports.aeq.admins.members.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.members.jpa.entity.AccountTa;
import de.esports.aeq.admins.security.exception.DuplicateEntityException;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountServiceBean implements AccountService {

    private final ModelMapper mapper;
    private final AccountRepository repository;

    public AccountServiceBean(ModelMapper mapper, AccountRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
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
            return AccountId.of(accountId.getValue(), accountId.getType());
        };

        mapper.addConverter(mapAccountId, AccountId.class, AccountIdTa.class);
        mapper.addConverter(mapAccountIdTa, AccountIdTa.class, AccountId.class);
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<Account> getAccounts() {
        return repository.findAll().stream().map(this::toAccount)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Account> getAccounts(Instant lastSeenAt) {
        return repository.findAllByLastSeenAfter(lastSeenAt).stream().map(this::toAccount)
                .collect(Collectors.toList());
    }

    @Override
    public Account getAccountById(AccountId accountId) {
        return repository.findById(accountId).map(this::toAccount)
                .orElseThrow(() -> new EntityNotFoundException(accountId));
    }

    @Override
    public Collection<Account> getAccountsByType(String type) {
        return repository.findAllByAccountIdType(type).stream().map(this::toAccount)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Account> getAccountsByType(String type, Instant lastSeenAt) {
        return repository.findAllByAccountIdTypeAndLastSeenAfter(type, lastSeenAt).stream()
                .map(this::toAccount).collect(Collectors.toList());
    }

    @Override
    public Account createAccount(Account account) {
        checkExistingAccountId(account.getAccountId());

        AccountTa entity = toAccountTa(account);
        entity.setCreatedAt(Instant.now());

        repository.save(entity);
        return toAccount(entity);
    }

    @Override
    public void deleteAccount(AccountId accountId) {
        repository.deleteById(accountId);
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<Complaint> getComplaints() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Complaint> getComplaints(AccountId accountId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Complaint> getSubmittedComplaints(AccountId accountId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addComplaint(AccountId accountId, Complaint complaint) {
        throw new UnsupportedOperationException();
    }


    //-----------------------------------------------------------------------

    private void checkExistingAccountId(AccountId accountId) {
        Optional<AccountTa> existing = repository.findById(accountId);
        if (existing.isPresent()) {
            throw new DuplicateEntityException(accountId);
        }
    }

    private Account toAccount(AccountTa account) {
        return mapper.map(account, Account.class);
    }

    private AccountTa toAccountTa(Account account) {
        return mapper.map(account, AccountTa.class);
    }
}
