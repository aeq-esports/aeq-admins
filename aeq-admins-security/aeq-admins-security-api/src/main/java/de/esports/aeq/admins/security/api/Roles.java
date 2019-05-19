package de.esports.aeq.admins.security.api;

public enum Roles {

    TRIAL_MEMBER("trial_member");

    private String name;

    Roles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
