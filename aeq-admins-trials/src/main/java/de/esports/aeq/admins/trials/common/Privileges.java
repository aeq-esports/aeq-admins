package de.esports.aeq.admins.trials.common;

public enum Privileges {

    READ_TRIAL_PERIOD("READ_TRIAL_PERIOD"),
    CREATE_TRIAL_PERIOD("CREATE_TRIAL_PERIOD"),
    UPDATE_TRIAL_PERIOD("UPDATE_TRIAL_PERIOD"),
    DELETE_TRIAL_PERIOD("DELETE_TRIAL_PERIOD");

    private final String name;

    Privileges(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
