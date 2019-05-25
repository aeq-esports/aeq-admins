package de.esports.aeq.admins.member.impl.service;

import de.esports.aeq.admins.member.api.service.MemberService;
import de.esports.aeq.admins.member.impl.MemberMapper;
import de.esports.aeq.admins.security.api.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
public class MemberServiceBean implements MemberService {

    private final MemberMapper mapper;
    private final AppUserService userDetailsService;

    @Autowired
    public MemberServiceBean(MemberMapper mapper, AppUserService userDetailsService) {
        this.mapper = mapper;
        this.userDetailsService = userDetailsService;
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<Member> getMembers() {
        return userDetailsService.getUsers().stream().map(mapper::toMember)
                .collect(collectingAndThen(toList(), this::mapMissingMemberData));
    }

    @Override
    public Optional<Member> getMemberById(Long userId) {
        return userDetailsService.getUserById(userId).map(mapper::toMember)
                .map(this::mapMissingMemberData);
    }

    @Override
    public Optional<Member> getMemberByUsername(String username) {
        return Optional.empty();
    }

    //-----------------------------------------------------------------------

    private Member mapMissingMemberData(Member member) {
        if (isTrialMember(member)) {
            member.setTrialMember(true);
        }
        return member;
    }

    private Collection<Member> mapMissingMemberData(Collection<Member> members) {
        for (Member member : members) {
            if (isTrialMember(member)) {
                member.setTrialMember(true);
            }
        }
        return members;
    }

    private boolean isTrialMember(Member member) {
        String roleName = Roles.TRIAL_MEMBER.getName();
        return member.getRoles().contains(roleName);
    }
}
