package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TrialPeriodAlreadyStartedException extends RuntimeException {

    private TrialPeriodTa trialPeriod;

    public TrialPeriodAlreadyStartedException(TrialPeriodTa trialPeriod) {
        this.trialPeriod = trialPeriod;
    }
}
