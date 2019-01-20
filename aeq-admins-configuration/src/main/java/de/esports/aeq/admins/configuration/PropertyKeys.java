package de.esports.aeq.admins.configuration;

public enum PropertyKeys {

    // Trial Period Properties
    TRIAL_DEFAULT_DURATION("aeq.admins.trials.duration"),
    TRIAL_DEFAULT_VESTING_PERIOD("aeq.admins.trials.vesting.period"),
    TRIAL_REQUIRED_VOTES("aeq.admins.trials.votes.required"),
    TRIAL_REQUIRED_VOTE_MAJORITY("aeq.admins.trials.votes.majority");

    private final String key;

    PropertyKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
