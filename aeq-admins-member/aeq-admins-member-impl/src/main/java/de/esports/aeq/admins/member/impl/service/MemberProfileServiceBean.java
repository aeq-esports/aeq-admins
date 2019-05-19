package de.esports.aeq.admins.member.impl.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.member.api.MemberProfile;
import de.esports.aeq.admins.member.api.service.MemberProfileService;
import de.esports.aeq.admins.member.impl.MemberMapper;
import de.esports.aeq.admins.member.impl.jpa.MemberProfileRepository;
import de.esports.aeq.admins.member.impl.jpa.entity.MemberProfileTa;
import de.esports.aeq.admins.security.api.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.esports.aeq.admins.common.ExampleTransformer.transform;
import static java.util.Objects.requireNonNull;

@Service
public class MemberProfileServiceBean implements MemberProfileService {

    private final MemberMapper mapper;
    private final AppUserService userDetailsService;
    private final MemberProfileRepository repository;

    @Autowired
    public MemberProfileServiceBean(MemberMapper mapper, AppUserService userDetailsService,
            MemberProfileRepository repository) {
        this.mapper = mapper;
        this.userDetailsService = userDetailsService;
        this.repository = repository;
    }

    @Override
    public Collection<MemberProfile> getProfiles() {
        return repository.findAll().stream().map(mapper::toMemberProfile)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MemberProfile> getProfileByUserId(Long userId) {
        return repository.findById(userId).map(mapper::toMemberProfile);
    }

    @Override
    public Collection<MemberProfile> getProfilesByExample(Example<MemberProfile> probe) {
        Example<MemberProfileTa> transformed = transform(probe, mapper::toMemberProfileTa);
        return repository.findAll(transformed).stream().map(mapper::toMemberProfile)
                .collect(Collectors.toList());
    }

    @Override
    public MemberProfile createProfile(MemberProfile profile) {
        Long userId = profile.getMemberId();
        userDetailsService.getUserById(userId);

        MemberProfileTa entity = mapper.toMemberProfileTa(profile);
        repository.save(entity);
        return mapper.toMemberProfile(entity);
    }

    @Override
    public MemberProfile updateProfile(MemberProfile profile) {
        Long userId = profile.getMemberId();
        requireNonNull(userId);

        MemberProfileTa existing = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId));

        MemberProfileTa entity = mapper.toMemberProfileTa(profile);
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
