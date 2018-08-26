package de.esports.aeq.admins.trials.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "aeq_trial")
public class TrialTa {

    @Id
    @GeneratedValue
    @Column(name = "aeq_trial_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TrialState state;

    @Column
    private LocalDateTime start;

    @Column
    private LocalDateTime end;

    @ManyToOne
    private List<TrialVoteTa> votes;

    @ManyToOne
    private List<TrialVoteTa> reviews;

    @ManyToOne
    private List<TrialFlagTa> flags;

}
