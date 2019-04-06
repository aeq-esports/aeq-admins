package de.esports.aeq.admins.platform.api.service;


import de.esports.aeq.admins.platform.api.Platform;

import java.util.Collection;
import java.util.Optional;

public interface PlatformService {

    Collection<Platform> getPlatforms();

    Optional<Platform> getPlatformById(Long id);

    Optional<Platform> getPlatformByType(String type);

    Optional<Platform> getPlatformByName(String id);

    Platform createPlatform(Platform platform);

    Platform updatePlatform(Platform platform);

    void deletePlatform(Long id);
}
