package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.common.TrialState;
import de.esports.aeq.admins.trials.service.dto.TrialPeriodConfig;
import de.esports.aeq.admins.trials.service.dto.TrialPeriodVote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
class DefaultTrialPeriodVoteEvaluator implements TrialPeriodVoteEvaluator {

    private final TrialPeriodConfigService service;

    @Autowired
    public DefaultTrialPeriodVoteEvaluator(
            TrialPeriodConfigService service) {
        this.service = service;
    }

    @Override
    public Optional<TrialState> evaluate(Collection<TrialPeriodVote> votes) {
        TrialPeriodConfig config = service.getConfig();

        int requiredVotes = config.getRequiredVotes();
        double majorityPercent = config.getRequiredVoteMajority();
        int amountOfEligibleVoters = 1; // TODO

        // assert that there are enough voters to fulfill the requirement
        if (requiredVotes > amountOfEligibleVoters) {
            requiredVotes = amountOfEligibleVoters;
        }
        if (votes.size() < requiredVotes) {
            return Optional.empty();
        }

        var consensusMap = votes.stream()
                .collect(Collectors.groupingBy(TrialPeriodVote::getConsensus,
                        Collectors.counting()));

        var maxEntry = consensusMap.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .orElseThrow();

        if (majorityPercent > 0.0) {
            double percent = maxEntry.getValue().doubleValue() / votes.size();
            if (percent < majorityPercent) {
                return Optional.empty();
            }
        }

        return Optional.of(maxEntry.getKey());
    }
}
