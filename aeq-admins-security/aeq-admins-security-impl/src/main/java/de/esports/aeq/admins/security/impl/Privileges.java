package de.esports.aeq.admins.security.impl;

public enum Privileges {

    PRIVILEGE_READ_PRIVILEGES("READ_PRIVILEGES");

    private final String name;

    Privileges(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
