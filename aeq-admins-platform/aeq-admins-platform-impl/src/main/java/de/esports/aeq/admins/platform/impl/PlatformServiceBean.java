package de.esports.aeq.admins.platform.impl;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.platform.api.Platform;
import de.esports.aeq.admins.platform.api.service.PlatformService;
import de.esports.aeq.admins.platform.impl.jpa.PlatformRepository;
import de.esports.aeq.admins.platform.api.entity.PlatformTa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class PlatformServiceBean implements PlatformService {

    private final PlatformMapper mapper;
    private final PlatformRepository repository;

    @Autowired
    public PlatformServiceBean(PlatformMapper mapper, PlatformRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
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
    public Optional<Platform> getPlatformByName(String name) {
        return repository.findByName(name).map(this::toPlatform);
    }

    @Override
    public Platform createPlatform(Platform platform) {
        requireNonNull(platform);
        platform.setId(null);

        PlatformTa entity = toPlatformTa(platform);
        repository.save(entity);

        return toPlatform(entity);
    }

    @Override
    public Platform updatePlatform(Platform platform) {
        requireNonNull(platform);

        Long platformId = platform.getId();
        PlatformTa existing = repository.findById(platformId)
                .orElseThrow(() -> new EntityNotFoundException(platformId));

        PlatformTa entity = toPlatformTa(platform);
        mapper.getMapper().map(entity, existing);

        repository.save(existing);
        return toPlatform(existing);
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
        return mapper.getMapper().map(platformTa, Platform.class);
    }

    private PlatformTa toPlatformTa(Platform platform) {
        return mapper.getMapper().map(platform, PlatformTa.class);
    }
}
