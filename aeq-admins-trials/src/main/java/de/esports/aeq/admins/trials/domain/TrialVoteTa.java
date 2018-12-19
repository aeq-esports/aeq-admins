package de.esports.aeq.admins.trials.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "aeq_trial_trial_vote")
public class TrialVoteTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "aeq_trial_vote_id")
    private Long id;

    @Column
    private boolean positive;

    @Column
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
