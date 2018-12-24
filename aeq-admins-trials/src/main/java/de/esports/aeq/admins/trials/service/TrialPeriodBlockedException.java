package de.esports.aeq.admins.trials.service;

import java.time.Instant;

public class TrialPeriodBlockedException extends RuntimeException {

    private final Instant blockedUntil;

    public TrialPeriodBlockedException(Instant blockedUntil) {
        this.blockedUntil = blockedUntil;
    }

    public Instant getBlockedUntil() {
        return blockedUntil;
    }
}
