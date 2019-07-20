package de.esports.aeq.admins.member.impl;

import de.esports.aeq.admins.common.MapperProvider;
import de.esports.aeq.admins.member.api.UserProfile;
import de.esports.aeq.admins.member.impl.jpa.entity.UserProfileTa;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper implements MapperProvider {

    private final ModelMapper mapper;

    public MemberMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    //-----------------------------------------------------------------------

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    //-----------------------------------------------------------------------

    /*
     * Converters.
     */

    public UserProfile toMemberProfile(UserProfileTa member) {
        return mapper.map(member, UserProfile.class);
    }

    public UserProfileTa toMemberProfileTa(UserProfile member) {
        return mapper.map(member, UserProfileTa.class);
    }
}
