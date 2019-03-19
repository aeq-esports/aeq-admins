package de.esports.aeq.admins.integration.riot;

import de.esports.aeq.admins.platform.api.Platform;
import de.esports.aeq.admins.platform.api.PlatformData;
import de.esports.aeq.admins.platform.api.PlatformType;
import de.esports.aeq.admins.platform.api.service.PlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;

@Component
public class PlatformProvider {

    private static final Logger LOG = LoggerFactory.getLogger(PlatformProvider.class);

    private final PlatformService platformService;

    @Autowired
    public PlatformProvider(PlatformService platformService) {
        this.platformService = platformService;
    }

    @PostConstruct
    public void setupPlatform() {
        LOG.info("Creating riot platform");

        PlatformType type = PlatformType.RIOT;
        Platform platform = Platform.create(type.toString(), type.getName());

        Collection<PlatformData> data = platform.getPlatformData();

        var platformValues = net.rithms.riot.constant.Platform.values();
        Arrays.stream(platformValues)
                .map(net.rithms.riot.constant.Platform::toString)
                .map(this::createPlatformData)
                .forEach(data::add);

        platformService.createPlatform(platform);
    }

    private PlatformData createPlatformData(String platformName) {
        return new RiotPlatformData(platformName);
    }

}
