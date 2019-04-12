package de.esports.aeq.admins.platform.api.service;


import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.platform.api.Platform;
import de.esports.aeq.admins.platform.api.PlatformType;

import java.util.Collection;
import java.util.Optional;

public interface PlatformService {

    Collection<Platform> getPlatforms();

    Optional<Platform> getPlatformById(Long id);

    Optional<Platform> getPlatformByType(String type);

    default Optional<Platform> getPlatformByType(Enum<? extends PlatformType> type) {
        return getPlatformByType(type.toString());
    }

    /**
     * Obtains the internal platform reference.
     *
     * @return the platform
     */
    default Platform getInternalPlatform() {
        return getPlatformByType(PlatformType.SYSTEM)
                .orElseThrow(() -> new EntityNotFoundException(PlatformType.SYSTEM));
    }

    Optional<Platform> getPlatformByName(String id);

    Platform createPlatform(Platform platform);

    Platform updatePlatform(Platform platform);

    void deletePlatform(Long id);
}
