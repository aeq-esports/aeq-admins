package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.members.domain.Member;
import de.esports.aeq.admins.members.domain.account.AccountId;

import java.util.Collection;

public interface MemberService {

    Collection<Member> getMembers();

    Member getMemberById(Long id);

    Member getMemberByAccountId(AccountId accountId);

    Member createMember(Member member);

    Member updateMember(Member member);

    void deleteMember(Long id);
}
