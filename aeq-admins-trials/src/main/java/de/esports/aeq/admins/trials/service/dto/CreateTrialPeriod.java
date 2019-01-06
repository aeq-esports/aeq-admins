package de.esports.aeq.admins.trials.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

public class CreateTrialPeriod implements Serializable {

    @NotNull
    private Long userId;
    private Instant start;
    private Duration duration;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateTrialPeriod)) return false;
        CreateTrialPeriod that = (CreateTrialPeriod) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(start, that.start) &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, start, duration);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CreateTrialPeriod.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("start=" + start)
                .add("duration=" + duration)
                .toString();
    }
}
