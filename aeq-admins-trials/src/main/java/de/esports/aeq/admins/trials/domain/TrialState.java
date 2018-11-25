package de.esports.aeq.admins.trials.domain;

public enum TrialState {

    /**
     * The trial is open and currently accepting votes.
     */
    OPEN(false),
    /**
     * The trial is not accepting votes anymore and is waiting to be reviewed by moderators.
     */
    PENDING(false),
    /**
     * The member representing the trial has been accepted.
     * <p>
     * This state is a final state.
     */
    APPROVED(true),
    /**
     * The member representing the trial has been rejected.
     * <p>
     * This state is a final state.
     */
    REJECTED(true);

    private boolean terminal;

    TrialState(boolean terminal) {
        this.terminal = terminal;
    }

    public boolean isTerminal() {
        return terminal;
    }
}
