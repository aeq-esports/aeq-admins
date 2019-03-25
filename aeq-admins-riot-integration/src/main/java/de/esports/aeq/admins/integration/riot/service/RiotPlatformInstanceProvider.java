package de.esports.aeq.admins.integration.riot.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.integration.riot.domain.RiotPlatformInstance;
import de.esports.aeq.admins.integration.riot.jpa.RiotPlatformInstanceRepository;
import de.esports.aeq.admins.integration.riot.jpa.entity.RiotPlatformInstanceTa;
import de.esports.aeq.admins.platform.api.PlatformInstanceProvider;
import de.esports.aeq.admins.platform.api.PlatformType;
import de.esports.aeq.admins.platform.api.entity.PlatformTa;
import de.esports.aeq.admins.platform.api.service.PlatformService;
import de.esports.aeq.admins.security.exception.DuplicateEntityException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class RiotPlatformInstanceProvider implements PlatformInstanceProvider<RiotPlatformInstance> {

    private final ModelMapper mapper;
    private final PlatformService platformService;
    private final RiotPlatformInstanceRepository repository;

    @Autowired
    public RiotPlatformInstanceProvider(ModelMapper mapper,
            PlatformService platformService,
            RiotPlatformInstanceRepository repository) {
        this.mapper = mapper;
        this.platformService = platformService;
        this.repository = repository;
    }

    //-----------------------------------------------------------------------

    /*
     * Platform data static and does not change.
     */

    @PostConstruct
    private void updateStaticPlatformInstances() {
        String platformType = PlatformType.RIOT.toString();
        PlatformTa platform = platformService.getPlatformByType(platformType)
                .map(plt -> mapper.map(plt, PlatformTa.class))
                .orElseThrow(() -> new EntityNotFoundException(platformType));

        Arrays.stream(net.rithms.riot.constant.Platform.values())
                .map(plt -> new RiotPlatformInstance(plt.getId(), plt.getName()))
                .map(this::toRiotPlatformInstanceTa)
                .peek(instance -> instance.setPlatform(platform))
                .forEach(this::createOrUpdate);
    }

    //-----------------------------------------------------------------------

    @Override
    public Class<RiotPlatformInstance> getType() {
        return RiotPlatformInstance.class;
    }

    @Override
    public Collection<RiotPlatformInstance> getPlatformInstances() {
        return repository.findAll().stream().map(this::toRiotPlatformInstance)
                .collect(Collectors.toList());
    }

    @Override
    public RiotPlatformInstance get(Long instanceId) {
        return repository.findById(instanceId).map(this::toRiotPlatformInstance)
                .orElseThrow(() -> new EntityNotFoundException(instanceId));
    }

    @Override
    public RiotPlatformInstance create(RiotPlatformInstance instance) {
        requireNonNull(instance);
        RiotPlatformInstanceTa entity = toRiotPlatformInstanceTa(instance);
        RiotPlatformInstanceTa created = create(entity);
        return toRiotPlatformInstance(created);
    }

    private RiotPlatformInstanceTa create(RiotPlatformInstanceTa instance) {
        instance.setId(null);

        repository.findByRegionId(instance.getRegionId()).ifPresent(e -> {
            throw new DuplicateEntityException(e);
        });

        return repository.save(instance);
    }

    @Override
    public RiotPlatformInstance update(RiotPlatformInstance instance) {
        requireNonNull(instance);

        RiotPlatformInstanceTa entity = toRiotPlatformInstanceTa(instance);
        RiotPlatformInstanceTa updated = update(entity);

        return toRiotPlatformInstance(updated);
    }

    private RiotPlatformInstanceTa update(RiotPlatformInstanceTa instance) {
        Long instanceId = instance.getId();
        RiotPlatformInstanceTa existing = repository.findById(instanceId)
                .orElseThrow(() -> new EntityNotFoundException(instance));

        mapper.map(instance, existing);

        return repository.save(existing);
    }

    private RiotPlatformInstanceTa createOrUpdate(RiotPlatformInstanceTa instance) {
        if (instance.getId() == null) {
            return create(instance);
        }
        return repository.findById(instance.getId()).map(this::update)
                .orElseGet(() -> create(instance));
    }

    //-----------------------------------------------------------------------

    private RiotPlatformInstance toRiotPlatformInstance(RiotPlatformInstanceTa instance) {
        return mapper.map(instance, RiotPlatformInstance.class);
    }

    private RiotPlatformInstanceTa toRiotPlatformInstanceTa(RiotPlatformInstance instance) {
        return mapper.map(instance, RiotPlatformInstanceTa.class);
    }
}
