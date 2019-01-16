package de.esports.aeq.admins.trials.common;

import java.time.Duration;
import java.time.Period;

public interface TrialPeriodEnvironment {

    Duration getTrialPeriodDuration();

    Period getVestingPeriod();
}
