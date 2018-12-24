package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialState;

import java.time.Duration;
import java.time.Instant;

public class TrialPeriod {

    private Long id;
    private Long userId;
    private TrialState state;
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
