package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.service.dto.TrialPeriodVote;

import java.util.Collection;
import java.util.Optional;

/**
 *
 */
@FunctionalInterface
public interface TrialPeriodVoteEvaluator {

    Optional<TrialState> evaluate(Collection<TrialPeriodVote> votes);
}
