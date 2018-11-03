package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialPeriodTa;

public class TrialPeriodAlreadyStartedException extends RuntimeException {

    private TrialPeriodTa trialPeriod;

    public TrialPeriodAlreadyStartedException(TrialPeriodTa trialPeriod) {
        this.trialPeriod = trialPeriod;
    }
}
