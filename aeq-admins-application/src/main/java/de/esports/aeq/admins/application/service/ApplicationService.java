package de.esports.aeq.admins.application.service;

import de.esports.aeq.admins.common.CamundaRelated;

import java.util.List;

public interface ApplicationService {

    /**
     * Returns a list of candidate user names responsible for approving applications.
     *
     * @return a list of user names
     */
    @CamundaRelated
    List<String> getApprovalCandidateUsers();

    @CamundaRelated
    boolean requiresReview(Long applicationId);
}
