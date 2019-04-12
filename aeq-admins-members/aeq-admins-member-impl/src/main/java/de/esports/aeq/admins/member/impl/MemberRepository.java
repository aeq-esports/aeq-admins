package de.esports.aeq.admins.member.impl;

import de.esports.aeq.admins.account.api.AccountId;
import de.esports.aeq.admins.members.jpa.entity.MemberTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberTa, Long> {

    Optional<MemberTa> findByAccountAccountId(AccountId accountId);

    @Query(value = "select m from mer_member m where m.account.data.firstName = :firstName")
    Optional<MemberTa> findByFirstName(String firstName);
}
