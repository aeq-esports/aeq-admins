package de.esports.aeq.admins.member.impl.service;

import static de.esports.aeq.admins.common.ExampleTransformer.transform;
import static de.esports.aeq.admins.member.api.Constants.USER_PROFILE_SERVICE;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.member.api.UserProfile;
import de.esports.aeq.admins.member.api.event.UserProfileUpdatedEvent;
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
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Primary
@Service(USER_PROFILE_SERVICE)
public class UserProfileServiceBean implements UserProfileService {

    private static final Logger LOG = LoggerFactory.getLogger(UserProfileServiceBean.class);

    private final MemberMapper mapper;
    private final SecurityService securityService;
    private final UserProfileRepository repository;

    @Autowired
    private KafkaTemplate<String, Object> template;

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
        Page<UserProfileTa> profilePage = repository.findAll(pageable);
        List<UserProfile> profiles = profilePage.get().map(mapper::toMemberProfile)
            .collect(Collectors.toList());
        return new PageImpl<>(profiles, pageable, profilePage.getTotalElements());
    }

    @Override
    public List<UserProfile> getAll(Example<UserProfile> example) {
        Example<UserProfileTa> transformed = transform(example, mapper::toMemberProfileTa);
        return repository.findAll(transformed).stream().map(mapper::toMemberProfile)
            .collect(toList());
    }

    @Override
    public List<UserProfile> getAll(Example<UserProfile> example, Sort sort) {
        Example<UserProfileTa> transformed = transform(example, mapper::toMemberProfileTa);
        return repository.findAll(transformed, sort).stream().map(mapper::toMemberProfile)
            .collect(toList());
    }

    @Override
    public Collection<UserProfile> getAllByIds(Collection<Long> userIds) {
        return repository.findAllById(userIds).stream().map(mapper::toMemberProfile)
            .collect(Collectors.toList());
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
        return getOneById(profile.getUserId());
    }

    @Override
    public UserProfile create(UserProfile profile) {
        Long userId = profile.getUserId();
        User user = securityService.getOneById(userId)
            .orElseThrow(() -> new EntityNotFoundException(userId));
        LOG.debug("Creating user profile for user with id {}: {}", user.getId(), profile);

        validateProfile(profile);
        UserProfileTa entity = mapper.toMemberProfileTa(profile);
        repository.save(entity);
        return mapper.toMemberProfile(entity);
    }

    @Override
    public UserProfile update(UserProfile profile) {
        Long userId = profile.getUserId();
        requireNonNull(userId);

        UserProfileTa existing = repository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(userId));

        UserProfileTa entity = mapper.toMemberProfileTa(profile);
        mapper.getMapper().map(entity, existing);

        repository.save(entity);
        UserProfile updated = mapper.toMemberProfile(entity);

        // TODO pass copy of previous profile?
        UserProfileUpdatedEvent event = new UserProfileUpdatedEvent(profile, updated);
        template.send(UserProfileUpdatedEvent.KEY, event);

        return updated;
    }

    @Override
    public void remove(Long userId) {
        requireNonNull(userId);
        repository.deleteById(userId);
    }

    //-----------------------------------------------------------------------

    private void validateProfile(UserProfile profile) {
        LocalDate dateOfBirth = profile.getDateOfBirth();
        if (LocalDate.now().isBefore(dateOfBirth)) {
            throw new IllegalArgumentException("Date of birth must be in the future");
        }
        int minAge = 16; // TODO: create config property
        if (profile.getAge() < minAge) {
            throw new IllegalArgumentException("The minimum age is " + minAge);
        }
    }
}
