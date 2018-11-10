package de.esports.aeq.admins.trials.jpa;

import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrialPeriodRepository extends JpaRepository<TrialPeriodTa, Long> {

    List<TrialPeriodTa> findAllActiveByUserId(Long userId);
}
