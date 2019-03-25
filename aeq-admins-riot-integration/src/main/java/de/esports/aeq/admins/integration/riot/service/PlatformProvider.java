package de.esports.aeq.admins.integration.riot.service;

import de.esports.aeq.admins.integration.riot.domain.RiotPlatformInstance;
import de.esports.aeq.admins.platform.api.Platform;
import de.esports.aeq.admins.platform.api.PlatformType;
import de.esports.aeq.admins.platform.api.StaticPlatformProvider;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class PlatformProvider implements StaticPlatformProvider {

    @Override
    public Collection<Platform> getPlatforms() {
        return Collections.singletonList(getRiotPlatform());
    }

    private Platform getRiotPlatform() {
        PlatformType platformType = PlatformType.RIOT;
        String type = platformType.toString();

        Platform platform = new Platform();
        platform.setType(type);
        platform.setName(platformType.getName());
        platform.setInstanceClass(RiotPlatformInstance.class);

        return platform;
    }
}
