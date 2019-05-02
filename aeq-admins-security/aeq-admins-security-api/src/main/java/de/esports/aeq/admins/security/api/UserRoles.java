package de.esports.aeq.admins.security.api;

public enum UserRoles {

    TRIAL_MEMBER("trial_member");

    private String name;

    UserRoles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
