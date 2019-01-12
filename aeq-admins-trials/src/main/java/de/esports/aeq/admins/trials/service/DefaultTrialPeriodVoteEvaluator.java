package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.configuration.SystemConfiguration;
import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.service.dto.TrialPeriodVote;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultTrialPeriodVoteEvaluator implements TrialPeriodVoteEvaluator {

    private final SystemConfiguration configuration;

    public DefaultTrialPeriodVoteEvaluator(
            SystemConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Optional<TrialState> evaluate(Collection<TrialPeriodVote> votes) {
        int requiredVotes = 1; // TODO
        double majorityPercent = 0.0;
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
            double percent = maxEntry.getValue() / votes.size();
            if (percent < majorityPercent) {
                return Optional.empty();
            }
        }

        return Optional.of(maxEntry.getKey());
    }
}
