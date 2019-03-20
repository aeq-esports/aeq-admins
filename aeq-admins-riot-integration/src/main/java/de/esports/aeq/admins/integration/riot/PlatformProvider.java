package de.esports.aeq.admins.integration.riot;

import de.esports.aeq.admins.platform.api.Platform;
import de.esports.aeq.admins.platform.api.PlatformData;
import de.esports.aeq.admins.platform.api.PlatformType;
import de.esports.aeq.admins.platform.api.StaticPlatformProvider;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlatformProvider implements StaticPlatformProvider {

    @Override
    public Collection<Platform> getPlatforms() {
        return Collections.singletonList(getRiotPlatform());
    }

    private Platform getRiotPlatform() {
        PlatformType platformType = PlatformType.RIOT;
        String type = platformType.toString();

        Platform platform = Platform.create(type, platformType.getName());

        var platformValues = net.rithms.riot.constant.Platform.values();

        List<PlatformData> data = Arrays.stream(platformValues)
                .map(net.rithms.riot.constant.Platform::toString)
                .map(this::createPlatformData)
                .collect(Collectors.toList());

        platform.setPlatformData(data);
        return platform;
    }

    private PlatformData createPlatformData(String platformName) {
        return new RiotPlatformData(platformName);
    }
}
