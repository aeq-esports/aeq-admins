package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialState;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

public class TrialPeriod implements Serializable {

    private Long id;
    private Long userId;
    private TrialState state;
    private TrialStateTransition stateTransition = TrialStateTransition.NORMAL;
    private Instant start;
    private Duration duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TrialState getState() {
        return state;
    }

    public void setState(TrialState state) {
        this.state = state;
    }

    public TrialStateTransition getStateTransition() {
        return stateTransition;
    }

    public void setStateTransition(TrialStateTransition stateTransition) {
        this.stateTransition = stateTransition;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
