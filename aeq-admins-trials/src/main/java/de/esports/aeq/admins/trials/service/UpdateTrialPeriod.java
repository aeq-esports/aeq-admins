package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialState;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Duration;

public class UpdateTrialPeriod implements Serializable {

    @NotNull
    private Long id;
    private TrialState state;
    private Duration duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrialState getState() {
        return state;
    }

    public void setState(TrialState state) {
        this.state = state;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
