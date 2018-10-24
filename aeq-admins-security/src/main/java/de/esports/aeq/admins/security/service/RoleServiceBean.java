package de.esports.aeq.admins.security.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.security.domain.RoleTa;
import de.esports.aeq.admins.security.exception.DuplicateEntityException;
import de.esports.aeq.admins.security.jpa.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceBean implements RoleService {

    private final RoleRepository repository;
    private final PrivilegeService privileges;

    @Autowired
    public RoleServiceBean(RoleRepository repository, PrivilegeService privileges) {
        this.repository = repository;
        this.privileges = privileges;
    }

    @Override
    public RoleTa create(RoleTa entity) {
        repository.findOneByName(entity.getName()).ifPresent(role -> {
            throw new DuplicateEntityException(entity.getName());
        });
        // assert that each privilege exists
        entity.getPrivileges()
                .forEach(privilege -> privileges.findOneByName(privilege.getName()));
        return repository.save(entity);
    }

    @Override
    public RoleTa update(RoleTa entity) {
        repository.findById(entity.getId())
                .orElseThrow(() -> new EntityNotFoundException(entity.getId()));
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<RoleTa> findAll() {
        return repository.findAll();
    }

    @Override
    public RoleTa findByName(String name) {
        return repository.findOneByName(name)
                .orElseThrow(() -> new EntityNotFoundException(name));
    }
}
