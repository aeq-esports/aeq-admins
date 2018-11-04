package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialPeriodTa;

public interface TrialPeriodService {

    /**
     * Creates a new trial period for the user with the given id.
     *
     * @param trialPeriod the user id
     */
    void createTrialPeriod(TrialPeriodTa trialPeriod);

    void createTrialPeriodByUsername(String username);
}
