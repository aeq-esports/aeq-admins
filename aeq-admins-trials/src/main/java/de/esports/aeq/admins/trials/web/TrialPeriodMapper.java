package de.esports.aeq.admins.trials.web;

import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.web.UserResponseDTO;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class TrialPeriodMapper {

    private final ModelMapper mapper;
    private final TrialPeriodService service;

    @Autowired
    public TrialPeriodMapper(ModelMapper mapper, TrialPeriodService service) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostConstruct
    private void registerTypeMap() {
        Converter<UserTa, UserResponseDTO> toUserResponseDto =
                context -> mapper.map(UserTa.class, UserResponseDTO.class);
        mapper.addConverter(toUserResponseDto);

        mapper.createTypeMap(TrialPeriodCreateDTO.class, TrialPeriodTa.class)
                .setPostConverter(this::postConverter);
    }

    private TrialPeriodTa postConverter(
            MappingContext<TrialPeriodCreateDTO, TrialPeriodTa> context) {
        TrialPeriodTa destination = context.getDestination();

        if (destination.getStart() == null) {
            destination.setStart(Instant.now());
        }

        if (destination.getDuration() != null) {
            return destination;
        }
        Optional<ZonedDateTime> endTime = context.getSource().getEnd();
        Duration duration;
        if (endTime.isPresent()) {
            duration = Duration.between(context.getDestination().getStart(), endTime.get());
        } else {
            duration = service.getConfiguration().getTrialPeriod();
        }
        destination.setDuration(duration);
        return destination;
    }

    public ModelMapper getMapper() {
        return mapper;
    }
}
