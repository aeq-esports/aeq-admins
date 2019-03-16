package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.members.domain.account.Platform;

import java.util.Collection;

public interface PlatformService {

    Collection<Platform> getPlatforms();

    Platform getPlatformById(Long id);

    Platform getPlatformByName(String id);

    Platform createPlatform(Platform platform);

    Platform updatePlatform(Platform platform);

    void deletePlatform(Long id);
}
