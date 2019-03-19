package de.esports.aeq.admins.platform.impl;

import de.esports.aeq.admins.platform.api.Platform;
import de.esports.aeq.admins.platform.api.PlatformData;
import de.esports.aeq.admins.platform.api.data.PropertyPlatformData;
import de.esports.aeq.admins.platform.api.entity.PlatformDataTa;
import de.esports.aeq.admins.platform.api.entity.PlatformTa;
import de.esports.aeq.admins.platform.api.entity.PropertyPlatformDataTa;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PlatformMapper {

    private final ModelMapper mapper;

    @Autowired
    public PlatformMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(Platform.class, PlatformTa.class)
                .addMappings(mapping -> mapping.skip(PlatformTa::setData))
                .setPostConverter(this::mapPlatformData);

        mapper.createTypeMap(PlatformTa.class, Platform.class)
                .addMappings(mapping -> mapping.skip(Platform::setPlatformData))
                .setPostConverter(this::mapPlatformDataTa);
    }

    //-----------------------------------------------------------------------

    private PlatformTa mapPlatformData(MappingContext<Platform, PlatformTa> context) {
        var platformData = context.getDestination().getData();
        context.getSource().getPlatformData().stream().map(this::mapPlatformData)
                .forEach(platformData::add);

        platformData.forEach(data -> data.setPlatform(context.getDestination()));
        return context.getDestination();
    }

    private PlatformDataTa mapPlatformData(PlatformData data) {
        if (data instanceof PropertyPlatformData) {
            return mapper.map(data, PropertyPlatformDataTa.class);
        }

        throw new IllegalStateException("Unknown platform data type: " + data.getClass());
    }

    private Platform mapPlatformDataTa(MappingContext<PlatformTa, Platform> context) {
        var platformData = context.getDestination().getPlatformData();
        context.getSource().getData().stream().map(this::mapPlatformDataTa)
                .forEach(platformData::add);

        return context.getDestination();
    }

    private PlatformData mapPlatformDataTa(PlatformDataTa data) {
        if (data instanceof PropertyPlatformDataTa) {
            return mapper.map(data, PropertyPlatformData.class);
        }

        throw new IllegalStateException("Unknown platform data type: " + data.getClass());
    }

    public ModelMapper getMapper() {
        return mapper;
    }
}
