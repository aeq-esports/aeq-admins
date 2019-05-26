package de.esports.aeq.admins.security.api.service;

import de.esports.aeq.admins.common.CrudService;
import de.esports.aeq.admins.security.api.User;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface SecurityService extends CrudService<User, String> {

    @Override
    Collection<User> getAll();

    @Override
    List<User> getAll(Sort sort);

    @Override
    Page<User> getAll(Pageable pageable);

    @Override
    <S extends User> List<S> getAll(Example<S> example);

    @Override
    <S extends User> List<S> getAll(Example<S> example, Sort sort);

    @Override
    Collection<User> getAllByIds(Collection<String> userIds);

    Collection<User> getAllByRoleName(String name);

    Collection<User> getAllByAnyRoleName(Collection<String> roleNames);

    Collection<User> getAllByAllRoleNames(Collection<String> roleNames);

    @Override
    Optional<User> getOneById(String userId);

    Optional<User> getOneByUsername(String username);

    @Override
    Optional<User> getOne(User user);

    @Override
    User create(User user);

    @Override
    User update(User user);

    @Override
    void remove(String userId);

    @Override
    default User createOrUpdate(User user) {
        return CrudService.super.createOrUpdate(user);
    }

}
