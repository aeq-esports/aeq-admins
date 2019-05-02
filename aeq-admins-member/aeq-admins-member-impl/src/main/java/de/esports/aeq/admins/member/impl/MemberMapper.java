package de.esports.aeq.admins.member.impl;

import de.esports.aeq.admins.common.MapperProvider;
import de.esports.aeq.admins.member.api.Member;
import de.esports.aeq.admins.member.api.MemberProfile;
import de.esports.aeq.admins.member.impl.jpa.entity.MemberProfileTa;
import de.esports.aeq.admins.security.api.User;
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

    public Member toMember(User user) {
        return mapper.map(user, Member.class);
    }

    public MemberProfile toMemberProfile(MemberProfileTa member) {
        return mapper.map(member, MemberProfile.class);
    }

    public MemberProfileTa toMemberProfileTa(MemberProfile member) {
        return mapper.map(member, MemberProfileTa.class);
    }
}
