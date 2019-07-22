package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.service.dto.CreateTrialPeriodVote;
import de.esports.aeq.admins.trials.service.dto.TrialPeriodVote;
import de.esports.aeq.admins.trials.service.dto.UpdateTrialPeriodVote;

import java.util.Collection;

public interface TrialPeriodVoteService {

    TrialPeriodVote create(CreateTrialPeriodVote vote);

    TrialPeriodVote findOne(Long voteId);

    TrialPeriodVote findOneByUserId(String userId);

    Collection<TrialPeriodVote> findAll(Long trialPeriodId);

    TrialPeriodVote update(UpdateTrialPeriodVote vote);

    void delete(Long voteId);

    /**
     * @param trialPeriodId the trial period id
     */
    void evaluateVotes(Long trialPeriodId);
}
