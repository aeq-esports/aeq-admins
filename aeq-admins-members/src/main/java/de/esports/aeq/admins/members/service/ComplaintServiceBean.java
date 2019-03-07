package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.members.PlatformType;
import de.esports.aeq.admins.members.domain.AccountId;
import de.esports.aeq.admins.members.domain.Complaint;
import de.esports.aeq.admins.members.domain.Platform;
import de.esports.aeq.admins.members.jpa.ComplaintRepository;
import de.esports.aeq.admins.members.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.members.jpa.entity.ComplaintTa;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ComplaintServiceBean implements ComplaintService {

    private final ModelMapper mapper;
    private final PlatformService platformService;
    private final AccountService accountService;
    private final ComplaintRepository repository;

    public ComplaintServiceBean(ModelMapper mapper, PlatformService platformService, AccountService accountService, ComplaintRepository repository) {
        this.mapper = mapper;
        this.platformService = platformService;
        this.accountService = accountService;
        this.repository = repository;
    }

    @Override
    public Collection<Complaint> getComplaints() {
        return repository.findAll().stream().map(this::toComplaint)
                .collect(Collectors.toList());

    }

    @Override
    public Complaint getComplaintById(Long id) {
        ComplaintTa complaint = getComplaintByIdOrThrow(id);
        return toComplaint(complaint);
    }

    private ComplaintTa getComplaintByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public Collection<Complaint> getComplaintsByAccused(AccountId accountId) {
        return repository.findAllByAccusedAccountIds(toAccountIdTa(accountId)).stream()
                .map(this::toComplaint).collect(Collectors.toList());
    }

    @Override
    public Collection<Complaint> getComplaintsByAccuser(AccountId accountId) {
        return repository.findAllByAccuserAccountId(toAccountIdTa(accountId)).stream()
                .map(this::toComplaint).collect(Collectors.toList());
    }

    @Override
    public Complaint createComplaint(Complaint complaint) {
        Objects.requireNonNull(complaint);
        ComplaintTa entity = toComplaintTa(complaint);

        entity.setId(null);
        entity.setCreatedAt(Instant.now());

        AccountId reporter = complaint.getReporter();
        if (reporter != null) {
            resolveAccountId(reporter);
        }

        AccountId accuser = complaint.getAccuserAccountId();
        if (accuser != null) {
            resolveAccountId(accuser);
        }

        complaint.getAccusedAccountIds().forEach(this::resolveAccountId);

        repository.save(entity);
        return toComplaint(entity);
    }

    @Override
    public Complaint updateComplaint(Complaint complaint) {
        Objects.requireNonNull(complaint);
        ComplaintTa existing = getComplaintByIdOrThrow(complaint.getId());

        ComplaintTa entity = toComplaintTa(complaint);
        mapper.map(entity, existing);

        repository.save(existing);
        return toComplaint(existing);
    }

    @Override
    public void deleteComplaint(Long id) {

    }

    //-----------------------------------------------------------------------

    private void resolveAccountId(AccountId accountId) {
        accountService.resolve(accountId);
    }

    //-----------------------------------------------------------------------

    /*
     * Convenience methods to be used for mapping.
     */

    private Complaint toComplaint(ComplaintTa account) {
        return mapper.map(account, Complaint.class);
    }

    private ComplaintTa toComplaintTa(Complaint account) {
        return mapper.map(account, ComplaintTa.class);
    }

    private AccountIdTa toAccountIdTa(AccountId accountId) {
        return mapper.map(accountId, AccountIdTa.class);
    }
}
