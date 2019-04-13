package de.esports.aeq.admins.member.impl.jpa;

import de.esports.aeq.admins.account.api.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.member.impl.jpa.entity.MemberTa;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberTa, Long> {

    @Query("select m from MemberTa m inner join MemberAccountTa m_acc on m.id = m_acc.id where " +
            "m_acc.accountId = :accountId")
    Optional<MemberTa> findByAccountAccountId(AccountIdTa accountId);

    @Query("select m from MemberTa m where m.details.firstName = :firstName")
    Optional<MemberTa> findByFirstName(String firstName);

    @Query("select m from MemberTa m where m.id = :id")
    @EntityGraph(value = "MemberTa.connectedAccounts", type = EntityGraph.EntityGraphType.LOAD)
    Collection<MemberTa> findByIdWithConnectedAccounts(Long id);
}
