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
}
