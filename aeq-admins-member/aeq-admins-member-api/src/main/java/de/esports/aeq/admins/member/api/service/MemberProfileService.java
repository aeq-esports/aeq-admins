package de.esports.aeq.admins.member.api.service;

import de.esports.aeq.admins.member.api.MemberProfile;
import org.springframework.data.domain.Example;

import java.util.Collection;
        import java.util.Optional;

public interface MemberProfileService {

    Collection<MemberProfile> getProfiles();

    Optional<MemberProfile> getProfileByUserId(Long userId);

    Collection<MemberProfile> getProfilesByExample(Example<MemberProfile> probe);

    MemberProfile createProfile(MemberProfile profile);

    MemberProfile updateProfile(MemberProfile profile);

    void deleteProfile(Long userId);
}
