package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.members.domain.account.Platform;
import de.esports.aeq.admins.members.jpa.PlatformRepository;
import de.esports.aeq.admins.members.jpa.entity.PlatformTa;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PlatformServiceBean implements PlatformService {

    private final ModelMapper mapper;
    private final PlatformRepository repository;

    public PlatformServiceBean(ModelMapper mapper, PlatformRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    //-----------------------------------------------------------------------

    @PostConstruct
    private void setupMapper() {

    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<Platform> getPlatforms() {
        return repository.findAll().stream().map(this::toPlatform)
                .collect(Collectors.toList());
    }

    @Override
    public Platform getPlatformById(Long id) {
        PlatformTa platform = getPlatformByIdOrThrow(id);
        return toPlatform(platform);
    }

    private PlatformTa getPlatformByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public Platform getPlatformByName(String name) {
        return repository.findByName(name).map(this::toPlatform)
                .orElseThrow(() -> new EntityNotFoundException(name));
    }

    @Override
    public Platform createMember(Platform platform) {
        Objects.requireNonNull(platform);
        PlatformTa entity = toPlatformTa(platform);

        repository.save(entity);
        return toPlatform(entity);
    }

    @Override
    public Platform updatePlatform(Platform platform) {
        Objects.requireNonNull(platform);
        PlatformTa existing = getPlatformByIdOrThrow(platform.getId());

        PlatformTa entity = toPlatformTa(platform);
        mapper.map(entity, existing);

        repository.save(existing);
        return toPlatform(existing);
    }

    @Override
    public void deletePlatform(Long id) {
        Objects.requireNonNull(id);
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
