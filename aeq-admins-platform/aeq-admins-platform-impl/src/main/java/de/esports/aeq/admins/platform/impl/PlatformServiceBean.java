package de.esports.aeq.admins.platform.impl;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.platform.api.Platform;
import de.esports.aeq.admins.platform.api.StaticPlatformProvider;
import de.esports.aeq.admins.platform.api.entity.PlatformTa;
import de.esports.aeq.admins.platform.api.service.PlatformService;
import de.esports.aeq.admins.platform.impl.jpa.PlatformRepository;
import de.esports.aeq.admins.security.exception.DuplicateEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class PlatformServiceBean implements PlatformService {

    private static final Logger LOG = LoggerFactory.getLogger(PlatformServiceBean.class);

    private final ApplicationContext context;
    private final PlatformMapper mapper;
    private final PlatformRepository repository;

    @Autowired
    public PlatformServiceBean(PlatformMapper mapper, PlatformRepository repository,
            ApplicationContext context) {
        this.mapper = mapper;
        this.repository = repository;
        this.context = context;
    }

    //-----------------------------------------------------------------------

    @PostConstruct
    private void setup() {
        context.getBeansOfType(StaticPlatformProvider.class).values()
                .forEach(this::processPlatformProviders);
    }

    private void processPlatformProviders(StaticPlatformProvider provider) {
        LOG.debug("Processing platform provider {}", provider.getClass());

        Collection<PlatformTa> platforms = provider.getPlatforms().stream()
                .map(this::toPlatformTa).collect(Collectors.toList());

        for (PlatformTa platform : platforms) {
            LOG.debug("Processing platform of provider {}: {}", provider.getClass(), platform);
            createOrUpdatePlatform(platform);
        }
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
        PlatformTa created = toPlatformTa(platform);
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

        mapper.getMapper().map(platform, existing);

        return repository.save(existing);
    }

    @Override
    public void deletePlatform(Long id) {
        requireNonNull(id);
        repository.deleteById(id);
    }

    //-----------------------------------------------------------------------

    private PlatformTa createOrUpdatePlatform(PlatformTa platform) {
        return repository.findByType(platform.getType())
                .map(this::updatePlatform)
                .orElseGet(() -> createPlatform(platform));
    }

    //-----------------------------------------------------------------------

    /*
     * Convenience methods to be used for mapping.
     */

    private Platform toPlatform(PlatformTa platformTa) {
        Platform platform = mapper.getMapper().map(platformTa, Platform.class);
        return platform;
    }

    private PlatformTa toPlatformTa(Platform platform) {
        return mapper.getMapper().map(platform, PlatformTa.class);
    }
}
