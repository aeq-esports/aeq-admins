package de.esports.aeq.admins.trials.jpa;

import de.esports.aeq.admins.trials.jpa.domain.TrialPeriodVoteTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface TrialPeriodVoteRepository extends JpaRepository<TrialPeriodVoteTa, Long> {

    Optional<TrialPeriodVoteTa> findByUserId(Long userId);

    Collection<TrialPeriodVoteTa> findAllByTrialPeriodId(Long trialPeriodId);
}
