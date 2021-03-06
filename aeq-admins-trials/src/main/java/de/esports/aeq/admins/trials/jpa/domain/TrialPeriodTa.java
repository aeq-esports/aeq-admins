package de.esports.aeq.admins.trials.jpa.domain;

import de.esports.aeq.admins.trials.common.TrialState;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "aeq_trial_period")
@Audited(withModifiedFlag = true)
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
    @NotAudited
    private String userId;

    @Enumerated(EnumType.STRING)
    private TrialState state;

    @Column
    private Instant start;

    @Column
    private Duration duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public Instant getEnd() {
        return start.plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrialPeriodTa)) return false;
        TrialPeriodTa that = (TrialPeriodTa) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                state == that.state &&
                Objects.equals(start, that.start) &&
                Objects.equals(duration, that.duration);
    }

    @Override public int hashCode() {
        return Objects.hash(id, userId, state, start, duration);
    }

    @Override public String toString() {
        return new StringJoiner(", ", TrialPeriodTa.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId=" + userId)
                .add("state=" + state)
                .add("start=" + start)
                .add("duration=" + duration)
                .toString();
    }
}
