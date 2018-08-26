package de.esports.aeq.admins.trials.domain;

import javax.persistence.*;

@Entity
@Table(name = "aeq_trial_flag")
public class TrialFlagTa {

    @Id
    @GeneratedValue
    @Column(name = "aeq_trial_flag_id")
    private Long id;

    @Enumerated
    private TrialFlagType type;

    @Column
    private String comment;
}
