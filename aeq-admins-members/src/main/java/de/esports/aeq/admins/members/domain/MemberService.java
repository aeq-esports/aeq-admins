package de.esports.aeq.admins.members.domain;

import java.util.Collection;

public interface MemberService {

    Collection<Member> findAll();

    Member findById(Long id);

    Member create(Member member);

    Member update(Member member);

    void delete(Long id);
}
