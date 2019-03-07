package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.members.PlatformType;
import de.esports.aeq.admins.members.domain.Platform;

import java.util.Collection;

public interface PlatformService {

    Collection<Platform> getPlatforms();

    default Platform getDefaultPlatform() {
        return getPlatformByName(PlatformType.SYSTEM.toString());
    }

    Platform getPlatformById(Long id);

    Platform getPlatformByName(String id);

    Platform createMember(Platform platform);

    Platform updatePlatform(Platform platform);

    void deletePlatform(Long id);
}
