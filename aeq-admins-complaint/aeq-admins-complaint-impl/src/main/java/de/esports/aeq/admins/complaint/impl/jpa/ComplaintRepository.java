package de.esports.aeq.admins.complaint.impl.jpa;

import de.esports.aeq.admins.account.api.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.complaint.api.jpa.ComplaintTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ComplaintRepository extends JpaRepository<ComplaintTa, Long> {

    Collection<ComplaintTa> findAllByAccuserAccountId(AccountIdTa accuserId);

    Collection<ComplaintTa> findAllByAccusedAccountIds(AccountIdTa accusedId);
}
