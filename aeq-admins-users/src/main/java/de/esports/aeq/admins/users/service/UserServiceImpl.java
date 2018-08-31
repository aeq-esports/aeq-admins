package de.esports.aeq.admins.users.service;

import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.users.domain.UserTa;
import de.esports.aeq.admins.users.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserTa> findAll(Predicate predicate) {
        return Lists.newArrayList(repository.findAll(predicate));
    }

    @Override
    public Optional<UserTa> findOne(Predicate predicate) {
        return Optional.empty();
    }

    @Override
    public UserTa findOneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public UserTa findOneByTS3UId(String ts3UId) {
        return repository.findByTs3UId(ts3UId)
                .orElseThrow(() -> new EntityNotFoundException(ts3UId));
    }

    @Override
    public List<UserTa> findAll() {
        return repository.findAll();
    }

    @Override
    public List<UserTa> findOneByFirstName(String firstName) {
        return repository.findAllByFirstName(firstName);
    }

    @Override
    public UserTa create(UserTa user) {
        return repository.save(user);
    }

    @Override
    public UserTa update(UserTa user) {
        findOneById(user.getId());
        return repository.save(user);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
