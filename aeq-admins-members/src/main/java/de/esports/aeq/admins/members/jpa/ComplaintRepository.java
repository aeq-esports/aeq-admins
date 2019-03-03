package de.esports.aeq.admins.members.jpa;

import de.esports.aeq.admins.members.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.members.jpa.entity.ComplaintTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ComplaintRepository extends JpaRepository<ComplaintTa, Long> {

    Collection<ComplaintTa> findAllByAccuserAccountId(AccountIdTa accuserId);

    Collection<ComplaintTa> findAllByAccusedAccountIds(AccountIdTa accusedId);
}
