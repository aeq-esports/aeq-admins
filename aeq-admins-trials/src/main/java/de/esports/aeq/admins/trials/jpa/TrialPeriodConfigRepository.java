package de.esports.aeq.admins.trials.jpa;

import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrialPeriodConfigRepository extends JpaRepository<TrialPeriodConfigTa, Long> {

}
