package de.esports.aeq.admins.security.api.service;

import de.esports.aeq.admins.common.CrudService;
import de.esports.aeq.admins.security.api.User;
import de.esports.aeq.admins.security.api.UserRole;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public interface UserRoleService extends CrudService<UserRole, String> {

    Collection<GrantedAuthority> getAuthorities();

}
