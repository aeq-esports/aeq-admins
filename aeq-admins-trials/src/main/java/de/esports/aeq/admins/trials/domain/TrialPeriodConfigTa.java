package de.esports.aeq.admins.trials.domain;

import javax.persistence.*;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Entity
@Table(name = "aeq_trial_period_config")
public class TrialPeriodConfigTa {

    @Id
    @GeneratedValue
    @Column(name = "trial_period_config_id")
    private Long id;

    @Column
    private Duration trialPeriod = Duration.of(10, SECONDS);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duration getTrialPeriod() {
        return trialPeriod;
    }

    public void setTrialPeriod(Duration trialPeriod) {
        this.trialPeriod = trialPeriod;
    }
}
