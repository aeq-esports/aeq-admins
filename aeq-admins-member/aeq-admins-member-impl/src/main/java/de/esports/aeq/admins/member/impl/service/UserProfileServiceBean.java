package de.esports.aeq.admins.member.impl.service;

import static de.esports.aeq.admins.common.ExampleTransformer.transform;
import static de.esports.aeq.admins.member.api.Constants.USER_PROFILE_SERVICE;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.member.api.UserProfile;
import de.esports.aeq.admins.member.api.service.UserProfileService;
import de.esports.aeq.admins.member.impl.MemberMapper;
import de.esports.aeq.admins.member.impl.jpa.UserProfileRepository;
import de.esports.aeq.admins.member.impl.jpa.entity.UserProfileTa;
import de.esports.aeq.admins.security.api.User;
import de.esports.aeq.admins.security.api.service.SecurityService;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Primary
@Service(USER_PROFILE_SERVICE)
public class UserProfileServiceBean implements UserProfileService {

    private final MemberMapper mapper;
    private final SecurityService securityService;
    private final UserProfileRepository repository;

    //-----------------------------------------------------------------------

    @Autowired
    public UserProfileServiceBean(MemberMapper mapper, SecurityService securityService,
        UserProfileRepository repository) {
        this.mapper = mapper;
        this.securityService = securityService;
        this.repository = repository;
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<UserProfile> getAll() {
        return repository.findAll().stream().map(mapper::toMemberProfile).collect(toList());
    }

    @Override
    public List<UserProfile> getAll(Sort sort) {
        return repository.findAll(sort).stream().map(mapper::toMemberProfile).collect(toList());
    }

    @Override
    public Page<UserProfile> getAll(Pageable pageable) {
        return repository.findAll(pageable).stream().map(mapper::toMemberProfile).collect(toList());
    }

    @Override
    public <S extends UserProfile> List<S> getAll(Example<S> example) {
        Example<UserProfileTa> transformed = transform(example, mapper::toMemberProfileTa);
        return repository.findAll(transformed).stream().map(mapper::toMemberProfile)
            .collect(toList());
    }

    @Override
    public <S extends UserProfile> List<S> getAll(Example<S> example, Sort sort) {

    }

    @Override
    public Collection<UserProfile> getAllByIds(Collection<Long> longs) {
        return null;
    }

    @Override
    public Optional<UserProfile> getOneById(Long userId) {
        return repository.findById(userId).map(mapper::toMemberProfile);
    }

    @Override
    public Optional<UserProfile> getOneByUsername(String username) {
        User user = securityService.getOneByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
        return getOneById(user.getId());
    }

    @Override
    public Optional<UserProfile> getOne(UserProfile profile) {
        requireNonNull(profile, "The profile must not be null");
        return getOne(profile);
    }

    @Override
    public UserProfile create(UserProfile profile) {
        String userId = profile.getUserId();
        securityService.getOneById(userId)
            .orElseThrow(() -> new EntityNotFoundException(userId));
        // validity checks
        LocalDate dateOfBirth = profile.getDateOfBirth();
        if (LocalDate.now().isBefore(dateOfBirth)) {
            throw new IllegalArgumentException("Date of birth must be in the future");
        }
        int minAge = 16;
        if (profile.getAge() < minAge) {
            throw new IllegalArgumentException("The minimum age is " + minAge);
        }
        UserProfileTa entity = mapper.toMemberProfileTa(profile);
        repository.save(entity);
        return mapper.toMemberProfile(entity);
    }

    @Override
    public UserProfile update(UserProfile object) {
        Long userId = profile.getUserId();
        requireNonNull(userId);

        UserProfileTa existing = repository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(userId));

        UserProfileTa entity = mapper.toMemberProfileTa(profile);
        mapper.getMapper().map(entity, existing);

        repository.save(entity);
        return mapper.toMemberProfile(entity);
    }

    @Override
    public void remove(Long aLong) {
        requireNonNull(userId);
        repository.deleteById(userId);
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<UserProfile> getProfiles() {

    }

    @Override
    public Optional<UserProfile> getProfileByUsername(Long userId) {
        return repository.findById(userId).map(mapper::toMemberProfile);
    }


    @Override
    public Collection<UserProfile> getProfilesByExample(Example<UserProfile> probe) {
        Example<UserProfileTa> transformed = transform(probe, mapper::toMemberProfileTa);
        return repository.findAll(transformed).stream().map(mapper::toMemberProfile)
            .collect(toList());
    }

    @Override
    public UserProfile createProfile(UserProfile profile) {
        Long userId = profile.getUserId();
        securityService.getOneById(userId);

        UserProfileTa entity = mapper.toMemberProfileTa(profile);
        repository.save(entity);
        return mapper.toMemberProfile(entity);
    }

    @Override
    public UserProfile updateProfile(UserProfile profile) {
        Long userId = profile.getUserId();
        requireNonNull(userId);

        UserProfileTa existing = repository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(userId));

        UserProfileTa entity = mapper.toMemberProfileTa(profile);
        mapper.getMapper().map(entity, existing);

        repository.save(entity);
        return mapper.toMemberProfile(entity);
    }

    @Override
    public void deleteProfile(Long userId) {
        requireNonNull(userId);
        repository.deleteById(userId);
    }
}
