package de.esports.aeq.admins.member.impl.service;

import static de.esports.aeq.admins.member.api.Constants.SECURE_USER_PROFILE_SERVICE;

import de.esports.aeq.admins.member.api.UserProfile;
import de.esports.aeq.admins.member.api.service.UserProfileService;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Decorated {@link UserProfileService}.
 */
@Service(SECURE_USER_PROFILE_SERVICE)
public class SecureUserProfileService implements UserProfileService {

    private final UserProfileService service;

    //-----------------------------------------------------------------------

    @Autowired
    public SecureUserProfileService(UserProfileService service) {
        this.service = service;
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<UserProfile> getAll() {
        return service.getAll();
    }

    @Override
    public List<UserProfile> getAll(Sort sort) {
        return service.getAll(sort);
    }

    @Override
    public Page<UserProfile> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    public <S extends UserProfile> List<S> getAll(Example<S> example) {
        return service.getAll(example);
    }

    @Override
    public <S extends UserProfile> List<S> getAll(Example<S> example, Sort sort) {
        return service.getAll(example, sort);
    }

    @Override
    public Collection<UserProfile> getAllByIds(Collection<Long> ids) {
        return service.getAllByIds(ids);
    }

    @Override
    public Optional<UserProfile> getOneById(Long userId) {
        return service.getOneById(userId);
    }

    @Override
    public Optional<UserProfile> getOneByUsername(String username) {
        return service.getOneByUsername(username);
    }

    @Override
    public Optional<UserProfile> getOne(UserProfile object) {
        return service.getOne(object);
    }

    @Override
    public UserProfile create(UserProfile profile) {
        return service.create(profile);
    }

    @Override
    public UserProfile update(UserProfile profile) {
        return service.update(profile);
    }

    @Override
    public void remove(Long userId) {
        service.remove(userId);
    }
}
