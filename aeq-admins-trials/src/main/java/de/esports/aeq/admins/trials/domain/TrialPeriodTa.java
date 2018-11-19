package de.esports.aeq.admins.trials.domain;

import de.esports.aeq.admins.security.domain.UserTa;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

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

    public boolean isActive() {
        return state == TrialState.OPEN || state == TrialState.PENDING;
    }
}
