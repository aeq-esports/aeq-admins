package de.esports.aeq.admins.account.impl.service;

import de.esports.aeq.account.api.AccountGroup;
import de.esports.aeq.account.api.service.AccountGroupService;
import de.esports.aeq.admins.account.impl.jpa.AccountGroupRepository;
import de.esports.aeq.admins.account.impl.jpa.entity.AccountGroupTa;
import de.esports.aeq.admins.common.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class AccountGroupServiceBean implements AccountGroupService {

    private final ModelMapper mapper;
    private final AccountGroupRepository repository;

    @Autowired
    public AccountGroupServiceBean(ModelMapper mapper, AccountGroupRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    //-----------------------------------------------------------------------

    @Override
    public AccountGroup getAccounts(Long groupId) {
        requireNonNull(groupId);
        return repository.findById(groupId).map(this::toAccountGroup)
                .orElseThrow(() -> new EntityNotFoundException(groupId));
    }

    @Override
    public AccountGroup getAccountsByPlatform(Long groupId, Long platformId) {
        return null;
    }

    //-----------------------------------------------------------------------

    /*
     * Convenience methods to be used for mapping.
     */

    private AccountGroup toAccountGroup(AccountGroupTa accountGroup) {
        return mapper.map(accountGroup, AccountGroup.class);
    }

    private AccountGroupTa toAccountGroupTa(AccountGroup accountGroup) {
        return mapper.map(accountGroup, AccountGroupTa.class);
    }
}
