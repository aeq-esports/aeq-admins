package de.esports.aeq.admins.member.api.service;

import de.esports.aeq.admins.common.CrudService;
import de.esports.aeq.admins.member.api.UserProfile;
import java.util.Optional;

public interface UserProfileService extends CrudService<UserProfile, String> {

    Optional<UserProfile> getOneByUsername(String username);
}
