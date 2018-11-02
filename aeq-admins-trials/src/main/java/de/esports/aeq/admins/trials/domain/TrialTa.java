package de.esports.aeq.admins.trials.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "aeq_trial")
public class TrialTa implements Serializable {

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
}
