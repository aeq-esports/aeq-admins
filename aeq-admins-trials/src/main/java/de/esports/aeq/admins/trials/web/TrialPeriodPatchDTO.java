package de.esports.aeq.admins.trials.web;

import java.io.Serializable;
import java.time.Duration;

public class TrialPeriodPatchDTO implements Serializable {

    private Duration duration;

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
