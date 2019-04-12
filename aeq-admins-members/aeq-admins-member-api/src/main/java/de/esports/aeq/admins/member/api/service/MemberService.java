package de.esports.aeq.admins.member.api.service;

import de.esports.aeq.admins.account.api.AccountId;
import de.esports.aeq.admins.member.api.Member;

import java.util.Collection;

public interface MemberService {

    Collection<Member> getMembers();

    Member getMemberById(Long id);

    Member getMemberByAccountId(AccountId accountId);

    Member createMember(Member member);

    Member updateMember(Member member);

    void deleteMember(Long id);
}
