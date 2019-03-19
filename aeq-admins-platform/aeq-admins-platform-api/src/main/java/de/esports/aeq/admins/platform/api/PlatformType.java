package de.esports.aeq.admins.platform.api;

public enum PlatformType {

    TEAMSPEAK("Teamspeak"),
    STEAM("Steam"),
    RIOT("Riot");

    private final String name;

    PlatformType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
