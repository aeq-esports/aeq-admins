package de.esports.aeq.admins.platform.api;

public class Platforms {

    public static final Platform SYSTEM = createSystemPlatform();

    private static Platform createSystemPlatform() {
        Platform platform = new Platform();
        String system = PlatformType.SYSTEM.toString();
        platform.setName(system);
        platform.setType(system);
        return platform;
    }
}
