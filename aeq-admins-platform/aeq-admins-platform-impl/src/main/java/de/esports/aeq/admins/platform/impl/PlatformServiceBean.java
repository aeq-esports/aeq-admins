package de.esports.aeq.admins.platform.impl;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.platform.api.Platform;
import de.esports.aeq.admins.platform.api.PlatformInstance;
import de.esports.aeq.admins.platform.api.data.RiotPlatformInstance;
import de.esports.aeq.admins.platform.api.service.PlatformService;
import de.esports.aeq.admins.platform.impl.jpa.PlatformRepository;
import de.esports.aeq.admins.platform.impl.jpa.entity.PlatformInstanceTa;
import de.esports.aeq.admins.platform.impl.jpa.entity.PlatformTa;
import de.esports.aeq.admins.platform.impl.jpa.entity.RiotPlatformInstanceTa;
import de.esports.aeq.admins.security.exception.DuplicateEntityException;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class PlatformServiceBean implements PlatformService {

    private static final Logger LOG = LoggerFactory.getLogger(PlatformServiceBean.class);

    private final ModelMapper mapper;
    private final PlatformRepository repository;

    @Autowired
    public PlatformServiceBean(ModelMapper mapper, PlatformRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    //-----------------------------------------------------------------------

    /*
     * Mapper configuration.
     */

    @PostConstruct
    private void configureMapper() {


        Function<PlatformInstance, PlatformInstanceTa> mapPlatformInstance = instance -> {
            if (instance instanceof RiotPlatformInstance) {
                return mapper.map(instance, RiotPlatformInstanceTa.class);
            }
            throw new IllegalArgumentException("Illegal class " + instance.getClass());
        };

        Function<PlatformInstanceTa, PlatformInstance> mapPlatformInstanceTa = instanceTa -> {
            if (instanceTa instanceof RiotPlatformInstanceTa) {
                return mapper.map(instanceTa, RiotPlatformInstance.class);
            }
            throw new IllegalArgumentException("Illegal class " + instanceTa.getClass());
        };

        Converter<Platform, PlatformTa> mapPlatform = c -> {
            c.getSource().getInstances().stream().map(mapPlatformInstance)
                    .forEach(c.getDestination().getInstances()::add);
            return c.getDestination();
        };

        Converter<PlatformTa, Platform> mapPlatformTa = c -> {
            c.getSource().getInstances().stream().map(mapPlatformInstanceTa)
                    .forEach(c.getDestination().getInstances()::add);
            return c.getDestination();
        };

        mapper.createTypeMap(Platform.class, PlatformTa.class)
                .addMappings(m -> m.skip(PlatformTa::setInstances))
                .setPostConverter(mapPlatform);

        mapper.createTypeMap(PlatformTa.class, Platform.class)
                .addMappings(m -> m.skip(Platform::setInstances))
                .setPostConverter(mapPlatformTa);
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<Platform> getPlatforms() {
        return repository.findAll().stream().map(this::toPlatform)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Platform> getPlatformById(Long id) {
        return repository.findById(id).map(this::toPlatform);
    }

    @Override
    public Optional<Platform> getPlatformByType(String type) {
        return repository.findByType(type).map(this::toPlatform);
    }

    @Override
    public Optional<Platform> getPlatformByName(String name) {
        return repository.findByName(name).map(this::toPlatform);
    }

    @Override
    public Platform createPlatform(Platform platform) {
        requireNonNull(platform);
        PlatformTa entity = toPlatformTa(platform);
        PlatformTa created = createPlatform(entity);
        return toPlatform(created);
    }

    private PlatformTa createPlatform(PlatformTa platform) {
        platform.setId(null);

        repository.findByType(platform.getType()).ifPresent(e -> {
            throw new DuplicateEntityException(e);
        });

        return repository.save(platform);
    }

    @Override
    public Platform updatePlatform(Platform platform) {
        requireNonNull(platform);

        PlatformTa entity = toPlatformTa(platform);
        PlatformTa updated = updatePlatform(entity);

        return toPlatform(updated);
    }

    private PlatformTa updatePlatform(PlatformTa platform) {
        Long platformId = platform.getId();
        PlatformTa existing = repository.findById(platformId)
                .orElseThrow(() -> new EntityNotFoundException(platformId));

        mapper.map(platform, existing);

        return repository.save(existing);
    }

    @Override
    public void deletePlatform(Long id) {
        requireNonNull(id);
        repository.deleteById(id);
    }

    //-----------------------------------------------------------------------

    /*
     * Convenience methods to be used for mapping.
     */

    private Platform toPlatform(PlatformTa platformTa) {
        return mapper.map(platformTa, Platform.class);
    }

    private PlatformTa toPlatformTa(Platform platform) {
        return mapper.map(platform, PlatformTa.class);
    }
}
