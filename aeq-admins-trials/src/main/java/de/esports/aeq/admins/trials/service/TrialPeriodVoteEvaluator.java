package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.service.dto.TrialPeriodVote;

import java.util.Collection;

public interface TrialPeriodVoteEvaluator {

    boolean evaluate(Long trialPeriodId, Collection<TrialPeriodVote> votes);
}
