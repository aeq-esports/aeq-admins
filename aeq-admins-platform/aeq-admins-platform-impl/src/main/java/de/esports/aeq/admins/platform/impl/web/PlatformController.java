package de.esports.aeq.admins.platform.impl.web;

import de.esports.aeq.admins.platform.api.Platform;
import de.esports.aeq.admins.platform.api.PlatformInstance;
import de.esports.aeq.admins.platform.api.PlatformInstanceService;
import de.esports.aeq.admins.platform.api.service.PlatformService;
import de.esports.aeq.admins.platform.impl.web.dto.PlatformDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/platform")
public class PlatformController {

    private final ModelMapper mapper;
    private final PlatformService platformService;
    private final PlatformInstanceService instanceService;

    @Autowired
    public PlatformController(ModelMapper mapper, PlatformService platformService,
            PlatformInstanceService instanceService) {
        this.mapper = mapper;
        this.platformService = platformService;
        this.instanceService = instanceService;
    }

    @GetMapping
    @ResponseBody
    public List<PlatformDto> findAll() {
        return platformService.getPlatforms().stream().map(this::toPlatformDto)
                .collect(Collectors.toList());
    }

    private PlatformDto toPlatformDto(Platform platform) {
        PlatformDto result = mapper.map(platform, PlatformDto.class);
        mapPlatformInstances(result, platform.getInstanceClass());
        return result;
    }

    private void mapPlatformInstances(PlatformDto platform, Class<?> instanceClass) {
        Class<PlatformInstance> targetClass = (Class<PlatformInstance>) instanceClass;
        Collection<PlatformInstance> instances = instanceService.getPlatformInstances(targetClass);
        platform.getInstances().addAll(instances);
    }
}
