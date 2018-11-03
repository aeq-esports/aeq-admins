package de.esports.aeq.admins.trials.service;

public interface TrialPeriodService {

    /**
     * Starts a new trial period for the user with the given id.
     *
     * @param userId the user id
     */
    void startTrialPeriod(Long userId);
}
