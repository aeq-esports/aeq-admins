package de.esports.aeq.admins.trials.service.dto;

import de.esports.aeq.admins.trials.common.TrialState;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

public class TrialPeriod implements Serializable {

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

    public Instant getEnd() {
        if (start != null && duration != null) {
            return start.plus(duration);
        }
        return null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrialPeriod)) return false;
        TrialPeriod that = (TrialPeriod) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                state == that.state &&
                Objects.equals(start, that.start) &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, state, start, duration);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TrialPeriod.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId=" + userId)
                .add("state=" + state)
                .add("start=" + start)
                .add("duration=" + duration)
                .toString();
    }
}
