package de.esports.aeq.admins.security.api.service;

import de.esports.aeq.admins.security.api.User;
import de.esports.aeq.admins.security.api.UserRole;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;

public interface SecurityService {

    Collection<User> getUsers();

    List<User> getUsers(Sort sort);

    Page<User> getUsers(Pageable pageable);

    Collection<User> getUsersByIds(Collection<Long> userIds);

    Optional<User> getUserById(Long userId);

    Optional<User> getUserByUsername(String username);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long userId);

    //-----------------------------------------------------------------------

    Collection<UserRole> getRoles();

    Optional<UserRole> getRoleByName(String name);

    Collection<User> getUsersByRoleName(String name);

    Collection<User> getUsersByAnyRoleName(Collection<String> roleNames);

    Collection<User> getUsersByAllRoleNames(Collection<String> roleNames);

    UserRole createUserRole(UserRole role);

    UserRole updateUserRole(UserRole role);

    void deleteUserRole(String name);

    Collection<GrantedAuthority> getAuthorities();
}
