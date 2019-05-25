package de.esports.aeq.admins.security.api.service;

import de.esports.aeq.admins.common.CrudService;
import de.esports.aeq.admins.security.api.User;
import java.util.Collection;
import java.util.Optional;

public interface SecurityService extends CrudService<User, Long> {

    Optional<User> getOneByUsername(String username);

    Collection<User> getAllByRoleName(String name);

    Collection<User> getAllByAnyRoleName(Collection<String> roleNames);

    Collection<User> getAllByAllRoleNames(Collection<String> roleNames);

}
