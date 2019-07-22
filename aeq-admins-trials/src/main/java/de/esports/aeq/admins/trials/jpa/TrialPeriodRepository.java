package de.esports.aeq.admins.trials.jpa;

import de.esports.aeq.admins.trials.jpa.domain.TrialPeriodTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrialPeriodRepository extends JpaRepository<TrialPeriodTa, Long> {

    List<TrialPeriodTa> findAllActiveByUserId(String userId);

    // TODO
    Optional<TrialPeriodTa> findLatestByUserId(String userId);
}
