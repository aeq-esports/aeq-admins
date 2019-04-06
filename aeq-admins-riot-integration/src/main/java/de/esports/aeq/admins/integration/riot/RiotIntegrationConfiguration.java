package de.esports.aeq.admins.integration.riot;

import de.esports.aeq.admins.platform.api.Platform;
import de.esports.aeq.admins.platform.api.PlatformInstance;
import de.esports.aeq.admins.platform.api.PlatformType;
import de.esports.aeq.admins.platform.api.data.RiotPlatformInstance;
import de.esports.aeq.admins.platform.api.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
public class RiotIntegrationConfiguration {

    private final PlatformService service;

    @Autowired
    public RiotIntegrationConfiguration(PlatformService service) {
        this.service = service;
    }

    @PostConstruct
    private void registerPlatform() {
        PlatformType platformType = PlatformType.RIOT;
        String type = platformType.toString();

        Platform platform = new Platform();
        platform.setType(type);
        platform.setName(platformType.getName());
        Collection<PlatformInstance> instances = getPlatformInstances();
        platform.setInstances(instances);

        service.getPlatformByType(type).ifPresentOrElse(plt -> {
                    plt.setInstances(getPlatformInstances());
                    service.updatePlatform(plt);
                },
                () -> service.createPlatform(platform));
    }

    private Collection<PlatformInstance> getPlatformInstances() {
        return Arrays.stream(net.rithms.riot.constant.Platform.values())
                .map(plt -> new RiotPlatformInstance(plt.getId(), plt.getName()))
                .collect(Collectors.toList());
    }
}
