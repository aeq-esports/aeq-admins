package de.esports.aeq.admins.member.api.service;

import java.util.Collection;
import java.util.Optional;

public interface MemberService {

    Collection<Member> getMembers();

    Optional<Member> getMemberById(Long userId);

    Optional<Member> getMemberByUsername(String username);
}
