package de.esports.aeq.admins.member.impl.service;

import de.esports.aeq.admins.member.api.Member;
import de.esports.aeq.admins.member.api.service.MemberService;
import de.esports.aeq.admins.member.impl.MemberMapper;
import de.esports.aeq.admins.security.api.UserRoles;
import de.esports.aeq.admins.security.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
public class MemberServiceBean implements MemberService {

    private final MemberMapper mapper;
    private final UserService userService;

    @Autowired
    public MemberServiceBean(MemberMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<Member> getMembers() {
        return userService.findAll().stream().map(mapper::toMember)
                .collect(collectingAndThen(toList(), this::mapMissingMemberData));
    }

    @Override
    public Optional<Member> getMemberById(Long userId) {
        return userService.findById(userId).map(mapper::toMember)
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
        String roleName = UserRoles.TRIAL_MEMBER.getName();
        return member.getRoles().contains(roleName);
    }
}
