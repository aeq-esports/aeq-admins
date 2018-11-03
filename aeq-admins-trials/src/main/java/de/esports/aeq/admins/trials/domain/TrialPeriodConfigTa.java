package de.esports.aeq.admins.trials.domain;

import javax.persistence.*;
import java.time.Period;

@Entity
@Table(name = "aeq_trial_period_config")
public class TrialPeriodConfigTa {

    @Id
    @GeneratedValue
    @Column(name = "trial_period_config_id")
    private Long id;

    @Column
    private Period trialPeriod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Period getTrialPeriod() {
        return trialPeriod;
    }

    public void setTrialPeriod(Period trialPeriod) {
        this.trialPeriod = trialPeriod;
    }
}
