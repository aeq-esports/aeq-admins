package de.esports.aeq.admins.security.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.security.domain.PrivilegeTa;
import de.esports.aeq.admins.security.jpa.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeServiceBean implements PrivilegeService {

    private final PrivilegeRepository repository;

    @Autowired
    public PrivilegeServiceBean(PrivilegeRepository repository) {
        this.repository = repository;
    }

    @Override
    public PrivilegeTa createIfNotExists(PrivilegeTa entity) {
        return repository.findByName(entity.getName())
                .orElseGet(() -> repository.save(entity));
    }

    @Override
    public List<PrivilegeTa> findAll() {
        return repository.findAll();
    }

    @Override
    public PrivilegeTa findOneByName(String name) {
        return repository.findByName(name)
                .orElseThrow(EntityNotFoundException::new);
    }
}
