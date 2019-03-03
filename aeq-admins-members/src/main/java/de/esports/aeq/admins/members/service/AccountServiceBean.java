package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.members.domain.Account;
import de.esports.aeq.admins.members.domain.AccountId;
import de.esports.aeq.admins.members.domain.Complaint;
import de.esports.aeq.admins.members.jpa.AccountRepository;
import de.esports.aeq.admins.members.jpa.ComplaintRepository;
import de.esports.aeq.admins.members.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.members.jpa.entity.AccountTa;
import de.esports.aeq.admins.members.jpa.entity.ComplaintTa;
import de.esports.aeq.admins.security.exception.DuplicateEntityException;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
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
    private final ComplaintRepository complaintRepository;

    public AccountServiceBean(ModelMapper mapper, AccountRepository accountRepository,
            ComplaintRepository complaintRepository) {
        this.mapper = mapper;
        this.accountRepository = accountRepository;
        this.complaintRepository = complaintRepository;
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

    @Override
    public Account createAccount(Account account) {
        checkExistingAccountId(account.getAccountId());

        AccountTa entity = toAccountTa(account);
        entity.setCreatedAt(Instant.now());

        accountRepository.save(entity);
        return toAccount(entity);
    }

    @Override
    public void deleteAccount(AccountId accountId) {
        accountRepository.deleteById(accountId);
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<Complaint> getComplaints() {
        return complaintRepository.findAll().stream().map(this::toComplaint)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Complaint> getComplaintsByAccused(AccountId accountId) {
        AccountIdTa mappedAccountId = toAccountIdTa(accountId);
        return complaintRepository.findAllByAccusedAccountIds(mappedAccountId).stream()
                .map(this::toComplaint).collect(Collectors.toList());
    }

    @Override
    public Collection<Complaint> getComplaintsByAccuser(AccountId accountId) {
        AccountIdTa mappedAccountId = toAccountIdTa(accountId);
        return complaintRepository.findAllByAccuserAccountId(mappedAccountId).stream()
                .map(this::toComplaint).collect(Collectors.toList());
    }

    @Override
    public Complaint addComplaint(Complaint complaint) {
        ComplaintTa entity = toComplaintTa(complaint);
        complaintRepository.save(entity);
        return toComplaint(entity);
    }

    //-----------------------------------------------------------------------

    /*
     * Assertion methods.
     */

    private void checkExistingAccountId(AccountId accountId) {
        Optional<AccountTa> existing = accountRepository.findById(accountId);
        if (existing.isPresent()) {
            throw new DuplicateEntityException(accountId);
        }
    }

    //-----------------------------------------------------------------------

    /*
     * Convenience methods to be used for mapping.
     */

    private Account toAccount(AccountTa account) {
        return mapper.map(account, Account.class);
    }

    private AccountTa toAccountTa(Account account) {
        return mapper.map(account, AccountTa.class);
    }

    private AccountIdTa toAccountIdTa(AccountId accountId) {
        return mapper.map(accountId, AccountIdTa.class);
    }

    private Complaint toComplaint(ComplaintTa account) {
        return mapper.map(account, Complaint.class);
    }

    private ComplaintTa toComplaintTa(Complaint account) {
        return mapper.map(account, ComplaintTa.class);
    }
}
