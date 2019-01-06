package de.esports.aeq.admins.trials.service.dto;

import de.esports.aeq.admins.trials.domain.TrialState;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;
import java.util.StringJoiner;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateTrialPeriod)) return false;
        UpdateTrialPeriod that = (UpdateTrialPeriod) o;
        return Objects.equals(id, that.id) &&
                state == that.state &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, duration);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UpdateTrialPeriod.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("state=" + state)
                .add("duration=" + duration)
                .toString();
    }
}
