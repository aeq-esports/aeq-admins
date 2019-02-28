package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.members.domain.Complaint;
import de.esports.aeq.admins.members.domain.Member;

import java.util.Collection;

public interface ComplaintService {

    Collection<Complaint> getComplaints();

    Complaint getComplaintById(Long id);

    Complaint createComplaint(Complaint complaint);

    Member updateMember(Member member);

    void deleteMember(Long id);
}
