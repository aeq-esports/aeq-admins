package de.esports.aeq.admins.security.api.service;

import de.esports.aeq.admins.security.api.User;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

public interface SecureSecurityService extends SecurityService {

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'READ') or "
        + "(cse.hasUsername(#username) and hasPermission('de.esports.aeq.admins.security.api.User', 'READ_SELF'))")
    Optional<User> getOneByUsername(String username);

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'READ')")
    Collection<User> getAllByRoleName(String name);

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'READ')")
    Collection<User> getAllByAnyRoleName(Collection<String> roleNames);

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'READ')")
    Collection<User> getAllByAllRoleNames(Collection<String> roleNames);

    @Override
    @PostFilter("hasPermission(filterObject, 'READ')")
    Collection<User> getAll();

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'READ')")
    List<User> getAll(Sort sort);

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'READ')")
    Page<User> getAll(Pageable pageable);

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'READ')")
    <S extends User> List<S> getAll(Example<S> example);

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'READ')")
    <S extends User> List<S> getAll(Example<S> example, Sort sort);

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'READ')")
    Collection<User> getAllByIds(Collection<String> userIds);

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'READ') or "
        + "(cse.hasUserId(#userId) and hasPermission('de.esports.aeq.admins.security.api.User', 'READ_SELF'))")
    Optional<User> getOneById(String userId);

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'READ') or "
        + "(cse.hasUserId(#user.id) and hasPermission('de.esports.aeq.admins.security.api.User', 'READ_SELF'))")
    Optional<User> getOne(User user);

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'WRITE')")
    User create(User user);

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'WRITE')")
    User update(User user);

    @Override
    @PreAuthorize("hasPermission('de.esports.aeq.admins.security.api.User', 'WRITE')")
    void remove(String userId);
}
