package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.service.UserService;
import de.esports.aeq.admins.security.web.UserResponseDTO;
import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.jpa.TrialPeriodConfigRepository;
import de.esports.aeq.admins.trials.jpa.TrialPeriodRepository;
import de.esports.aeq.admins.trials.web.TrialPeriodCreateDTO;
import de.esports.aeq.admins.trials.web.TrialPeriodResponseDTO;
import de.esports.aeq.admins.trials.workflow.ProcessVariables;
import org.camunda.bpm.engine.RuntimeService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrialPeriodServiceBean implements TrialPeriodService {

    private ModelMapper mapper;
    private UserService userService;
    private TrialPeriodRepository trialPeriodRepository;
    private TrialPeriodConfigRepository trialPeriodConfigRepository;
    private final RuntimeService runtimeService;

    @Autowired
    public TrialPeriodServiceBean(ModelMapper mapper, UserService userService,
            TrialPeriodRepository trialPeriodRepository,
            TrialPeriodConfigRepository trialPeriodConfigRepository,
            RuntimeService runtimeService) {
        this.mapper = mapper;
        this.userService = userService;
        this.trialPeriodRepository = trialPeriodRepository;
        this.trialPeriodConfigRepository = trialPeriodConfigRepository;
        this.runtimeService = runtimeService;
    }

    @PostConstruct
    private void registerTypeMap() {
        Converter<UserTa, UserResponseDTO> toUserResponseDto =
                context -> mapper.map(UserTa.class, UserResponseDTO.class);
        mapper.addConverter(toUserResponseDto);
    }

    //-----------------------------------------------------------------------
    @Override
    public List<TrialPeriodResponseDTO> findAll(Long userId) {
        var trialPeriods = trialPeriodRepository.findAll()
                .stream()
                .map(trialPeriod -> mapper.map(trialPeriod, TrialPeriodResponseDTO.class))
                .collect(Collectors.toList());
        trialPeriods.forEach(trialPeriod ->
                trialPeriod.setEnd(trialPeriod.getStart().plus(trialPeriod.getDuration())));
        return trialPeriods;
    }

    //-----------------------------------------------------------------------

    @Override
    public TrialPeriodResponseDTO findOne(Long trialPeriodId) {
        return trialPeriodRepository.findById(trialPeriodId)
                .map(trialPeriod -> mapper.map(trialPeriod, TrialPeriodResponseDTO.class))
                .orElseThrow(() -> new EntityNotFoundException(trialPeriodId));
    }

    //-----------------------------------------------------------------------
    @Override
    public void createTrialPeriod(Long userId, TrialPeriodCreateDTO request) {
        // fail fast if no user is present
        UserTa user = userService.findById(userId);
        assertNoActiveTrialPeriodOrThrow(user.getId());

        TrialPeriodTa trialPeriod = new TrialPeriodTa();
        trialPeriod.setUser(user);

        Instant start = getTrialPeriodStart(request);
        trialPeriod.setStart(start);

        Duration duration = getTrialPeriodDuration(request, start);
        trialPeriod.setDuration(duration);

        trialPeriod.setState(TrialState.OPEN);
        TrialPeriodTa entity = trialPeriodRepository.save(trialPeriod);

        // startTrialPeriodWorkflow(entity);
    }

    private Duration getTrialPeriodDuration(TrialPeriodCreateDTO request, Instant start) {
        if (request.getDuration().isPresent()) {
            // TODO: check permission
            return request.getDuration().get();
        }
        if (request.getEnd().isPresent()) {
            // TODO: check permission
            return Duration.between(start, request.getEnd().get());
        }
        return getConfiguration().getTrialPeriod();
    }

    private Instant getTrialPeriodStart(TrialPeriodCreateDTO request) {
        if (request.getStart().isPresent()) {
            // TODO: check permission
            return request.getStart().get().toInstant();
        }
        return Instant.now();
    }

    //-----------------------------------------------------------------------
    @Override
    public TrialPeriodTa update(TrialPeriodTa trialPeriod) {
        // TODO send stream message
        return trialPeriod;
    }

    //-----------------------------------------------------------------------
    @Override
    public TrialPeriodConfigTa getConfiguration() {
        return trialPeriodConfigRepository.findAll().stream()
                .findFirst().orElseThrow();
    }

    @Override
    public void updateConfiguration(TrialPeriodConfigTa config) {
        trialPeriodConfigRepository.save(config);
    }

    //-----------------------------------------------------------------------
    private void startTrialPeriodWorkflow(TrialPeriodTa entity) {
        runtimeService.createProcessInstanceByKey(ProcessVariables.TRIAL_PERIOD_DEFINITION_KEY)
                .setVariable(ProcessVariables.TRIAL_PERIOD_ID, entity.getId())
                .execute();
    }

    //-----------------------------------------------------------------------

    /**
     * Asserts that the user with the given id does not have any active trial period, otherwise an
     * exception is thrown.
     * <p>
     * A trial period is considered active, if its state is not terminal.
     *
     * @param userId the user id
     */
    private void assertNoActiveTrialPeriodOrThrow(Long userId) {
        trialPeriodRepository.findAllActiveByUserId(userId)
                .stream().filter(TrialPeriodTa::isActive).findAny().ifPresent(entity -> {
            throw new TrialPeriodAlreadyStartedException(entity);
        });
    }
}
