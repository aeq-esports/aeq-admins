package de.esports.aeq.admins.trials.jpa.domain;

import de.esports.aeq.admins.trials.common.TrialState;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Audited
@Table(name = "aeq_trial_period_vote")
public class TrialPeriodVoteTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "aeq_trial_period_vote_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trial_period_id")
    @NotAudited
    private TrialPeriodTa trialPeriod;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotAudited
    private Long userId;

    @Column
    private TrialState consensus;

    @Column
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrialPeriodTa getTrialPeriod() {
        return trialPeriod;
    }

    public void setTrialPeriod(TrialPeriodTa trialPeriod) {
        this.trialPeriod = trialPeriod;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TrialState getConsensus() {
        return consensus;
    }

    public void setConsensus(TrialState consensus) {
        this.consensus = consensus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
