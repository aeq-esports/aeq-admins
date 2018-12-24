package de.esports.aeq.admins.configuration;

public enum PropertyKeys {

    // Trial Period Properties
    TRIAL_DEFAULT_DURATION("aeq.admins.trials.duration"),
    TRIAL_DEFAULT_VESTING_PERIOD("aeq.admins.trials.vesting.period");

    private final String key;

    PropertyKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
