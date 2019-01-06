package de.esports.aeq.admins.trials.exception;

import de.esports.aeq.admins.trials.domain.TrialState;

public class IllegalTrialPeriodStateException extends RuntimeException {

    public IllegalTrialPeriodStateException(TrialState state) {
        super("Illegal trial period state: " + state);
    }
}
