package de.esports.aeq.admins.trials.common;

import de.esports.aeq.admins.configuration.PropertyKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Period;

@Component
class TrialPeriodEnvironmentBean implements TrialPeriodEnvironment {

    private final ConfigurableEnvironment environment;

    @Autowired
    public TrialPeriodEnvironmentBean(
            ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    public Duration getTrialPeriodDuration() {
        String key = PropertyKeys.TRIAL_DEFAULT_DURATION.getKey();
        String duration = environment.getRequiredProperty(key);
        return Duration.parse(duration);
    }

    public Period getVestingPeriod() {
        String key = PropertyKeys.TRIAL_DEFAULT_VESTING_PERIOD.getKey();
        String period = environment.getProperty(key);
        return period != null ? Period.parse(period) : Period.ZERO;
    }
}
