package de.esports.aeq.admins.trials.domain;

import de.esports.aeq.admins.security.domain.UserTa;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "aeq_trial_period")
@NamedQueries(
        @NamedQuery(name = "TrialPeriodTa.findAllActive",
                query = "SELECT t FROM TrialPeriodTa t WHERE t.user.id = :userId AND t.state = " +
                        "'OPEN' OR t.state = 'PENDING'")
)
public class TrialPeriodTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "trial_period_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTa user;

    @Enumerated(EnumType.STRING)
    private TrialState state;

    @Column
    private Instant start;

    @Column
    private Duration duration;

    public TrialPeriodTa() {

    }

    public TrialPeriodTa(TrialPeriodTa trialPeriod) {
        this.user = trialPeriod.user;
        this.state = trialPeriod.state;
        this.start = trialPeriod.start;
        this.duration = trialPeriod.duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserTa getUser() {
        return user;
    }

    public void setUser(UserTa user) {
        this.user = user;
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

    public Instant getEnd() {
        return start.plus(duration);
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
        if (!(o instanceof TrialPeriodTa)) return false;
        TrialPeriodTa that = (TrialPeriodTa) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                state == that.state &&
                Objects.equals(start, that.start) &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, state, start, duration);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TrialPeriodTa.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("user=" + user)
                .add("state=" + state)
                .add("start=" + start)
                .add("duration=" + duration)
                .toString();
    }
}
