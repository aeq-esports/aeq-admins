package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.members.domain.account.AccountId;
import de.esports.aeq.admins.members.domain.Complaint;

import java.util.Collection;

public interface ComplaintService {

    Collection<Complaint> getComplaints();

    Complaint getComplaintById(Long id);

    /**
     * Obtains all complaints that have been associated to this account.
     * <p>
     * More specifically, returns all complaints that list the holder of this account as one of the
     * <i>accused</i>.
     *
     * @return a <code>Collection</code> of complaints, which may be empty, but never
     * <code>null</code>
     */
    Collection<Complaint> getComplaintsByAccused(AccountId accountId);

    Collection<Complaint> getComplaintsByAccuser(AccountId accountId);

    Complaint createComplaint(Complaint complaint);

    Complaint updateComplaint(Complaint complaint);

    void deleteComplaint(Long id);
}
