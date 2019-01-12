package de.esports.aeq.admins.trials.web.dto;

import de.esports.aeq.admins.trials.domain.TrialState;

import java.io.Serializable;
import java.time.Duration;

public class TrialPeriodPatchDto implements Serializable {

    private TrialState state;
    private Duration duration;

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
