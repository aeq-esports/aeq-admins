package de.esports.aeq.admins.trials.domain;

public enum TrialState {

    /**
     * The trial is open and currently accepting votes.
     */
    OPEN,
    /**
     * The trial is not accepting votes anymore and is waiting to be reviewed by moderators.
     */
    PENDING,
    /**
     * The member representing the trial has been accepted.
     * <p>
     * This state is a final state.
     */
    APPROVED,
    /**
     * The member representing the trial has been rejected.
     * <p>
     * This state is a final state.
     */
    REJECTED
}
