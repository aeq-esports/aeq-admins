package de.esports.aeq.admins.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Period;

@Component
public class SystemConfigurationBean implements SystemConfiguration {

    private final Environment environment;

    @Autowired
    public SystemConfigurationBean(Environment environment) {
        this.environment = environment;
    }

    @Override
    public PasswordStrength getPasswordStrength() {
        return new PasswordStrength(8, true, true, true, true);
    }

    @Override
    public Duration getTrialPeriodDuration() {
        String key = PropertyKeys.TRIAL_DEFAULT_DURATION.getKey();
        String duration = environment.getRequiredProperty(key);
        return Duration.parse(duration);
    }

    @Override
    public Period getVestingPeriod() {
        String key = PropertyKeys.TRIAL_DEFAULT_VESTING_PERIOD.getKey();
        String period = environment.getProperty(key);
        return period != null ? Period.parse(period) : Period.ZERO;
    }
}
