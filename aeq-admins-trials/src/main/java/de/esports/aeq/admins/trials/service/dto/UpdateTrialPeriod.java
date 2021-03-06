package de.esports.aeq.admins.trials.service.dto;

import de.esports.aeq.admins.trials.common.TrialState;
import de.esports.aeq.admins.trials.common.TrialStateTransition;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;
import java.util.StringJoiner;

public class UpdateTrialPeriod implements Serializable {

    @NotNull
    private Long id;
    private TrialState state;
    private TrialStateTransition transition = TrialStateTransition.NORMAL;
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

    public TrialStateTransition getTransition() {
        return transition;
    }

    public void setTransition(TrialStateTransition transition) {
        this.transition = transition;
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
