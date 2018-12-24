package de.esports.aeq.admins.configuration;

import java.time.Duration;
import java.time.Period;

public interface SystemConfiguration {

    PasswordStrength getPasswordStrength();

    Duration getTrialPeriodDuration();

    Period getVestingPeriod();
}
