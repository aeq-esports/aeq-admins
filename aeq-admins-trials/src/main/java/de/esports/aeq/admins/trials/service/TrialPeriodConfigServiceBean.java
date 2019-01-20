package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.configuration.PropertyKeys;
import de.esports.aeq.admins.trials.service.dto.TrialPeriodConfig;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Period;

@Service
public class TrialPeriodConfigServiceBean implements TrialPeriodConfigService {

    private final ConfigurableEnvironment environment;
    private TrialPeriodConfig cachedInstance = null;

    public TrialPeriodConfigServiceBean(
            ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public TrialPeriodConfig getConfig() {
        if (cachedInstance == null) {
            updateCache();
        }
        return cachedInstance;
    }

    private void updateCache() {
        TrialPeriodConfig config = new TrialPeriodConfig();
        config.setTrialPeriodDuration(getTrialPeriodDuration());
        config.setVestingPeriod(getVestingPeriod());
        config.setRequiredVotes(getRequiredVotes());
        config.setRequiredVoteMajority(getRequiredVoteMajority());
        this.cachedInstance = config;
    }

    @Override
    public void updateConfig(TrialPeriodConfig config) {
        setTrialPeriodDuration(config.getTrialPeriodDuration());
        setVestingPeriod(config.getVestingPeriod());
        setRequiredVotes(config.getRequiredVotes());
        setRequiredVoteMajority(config.getRequiredVoteMajority());
        updateCache();
    }

    private Duration getTrialPeriodDuration() {
        String key = PropertyKeys.TRIAL_DEFAULT_DURATION.getKey();
        String duration = environment.getRequiredProperty(key);
        return Duration.parse(duration);
    }

    private void setTrialPeriodDuration(Duration duration) {
        String key = PropertyKeys.TRIAL_DEFAULT_DURATION.getKey();
        environment.getSystemEnvironment().put(key, duration.toString());
    }

    private Period getVestingPeriod() {
        String key = PropertyKeys.TRIAL_DEFAULT_VESTING_PERIOD.getKey();
        String period = environment.getProperty(key);
        return period != null ? Period.parse(period) : Period.ZERO;
    }

    private void setVestingPeriod(Period period) {
        String key = PropertyKeys.TRIAL_DEFAULT_VESTING_PERIOD.getKey();
        environment.getSystemEnvironment().put(key, period.toString());
    }

    private int getRequiredVotes() {
        String key = PropertyKeys.TRIAL_REQUIRED_VOTES.getKey();
        String amount = environment.getProperty(key);
        return amount != null ? Integer.parseInt(amount) : 1;
    }

    private void setRequiredVotes(int requiredVotes) {
        String key = PropertyKeys.TRIAL_REQUIRED_VOTES.getKey();
        environment.getSystemEnvironment().put(key, requiredVotes);
    }

    private double getRequiredVoteMajority() {
        String key = PropertyKeys.TRIAL_REQUIRED_VOTE_MAJORITY.getKey();
        String percent = environment.getProperty(key);
        return percent != null ? Double.parseDouble(percent) : 0.0;
    }

    private void setRequiredVoteMajority(double voteMajority) {
        String key = PropertyKeys.TRIAL_REQUIRED_VOTE_MAJORITY.getKey();
        environment.getSystemEnvironment().put(key, voteMajority);
    }
}
