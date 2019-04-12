package de.esports.aeq.admins.platform.api;

public enum PlatformType {

    TEAMSPEAK("Teamspeak"),
    STEAM("Steam"),
    RIOT("Riot"),
    SYSTEM("System");

    private final String name;

    PlatformType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
