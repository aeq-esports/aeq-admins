package de.esports.aeq.admins.security.api.service;

import de.esports.aeq.admins.common.CrudService;
import de.esports.aeq.admins.common.Referable;
import de.esports.aeq.admins.security.api.DefaultUser;
import java.util.Optional;

public interface AppUserService extends CrudService<DefaultUser, Long> {

    DefaultUser create(DefaultUser user, Referable referable);

    Optional<DefaultUser> getByUsername(String username);
}
