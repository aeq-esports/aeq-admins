package de.esports.aeq.admins.trials.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Indicates that a new trial period cannot be started since another one is already active.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TrialPeriodAlreadyStartedException extends RuntimeException {

    private final TrialPeriod trialPeriod;

    public TrialPeriodAlreadyStartedException(TrialPeriod trialPeriod) {
        this.trialPeriod = trialPeriod;
    }

    public TrialPeriod getTrialPeriod() {
        return trialPeriod;
    }
}
