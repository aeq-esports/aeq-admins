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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrialPeriodServiceBean implements TrialPeriodService {

    private static Logger LOG = LoggerFactory.getLogger(TrialPeriodServiceBean.class);

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
        return trialPeriodRepository.findAll().stream().map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    //-----------------------------------------------------------------------

    @Override
    public TrialPeriodResponseDTO findOne(Long trialPeriodId) {
        return trialPeriodRepository.findById(trialPeriodId)
                .map(this::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(trialPeriodId));
    }

    //-----------------------------------------------------------------------
    @Override
    public void createTrialPeriod(Long userId, TrialPeriodCreateDTO request) {
        // fail fast if no user is present
        UserTa user = userService.findById(userId);
        assertNoActiveTrialPeriodOrThrow(user.getId());


        TrialPeriodTa entity = createTrialPeriod(user, request);
        TrialPeriodTa savedEntity = trialPeriodRepository.save(entity);

        startTrialPeriodWorkflow(savedEntity);
        // TODO: post event to cloud-messaging
    }

    private TrialPeriodTa createTrialPeriod(UserTa user, TrialPeriodCreateDTO request) {
        TrialPeriodTa trialPeriod = new TrialPeriodTa();
        trialPeriod.setUser(user);

        Instant start = getTrialPeriodStart(request);
        trialPeriod.setStart(start);

        Duration duration = calculateTrialPeriodDuration(request, start);
        trialPeriod.setDuration(duration);

        trialPeriod.setState(TrialState.OPEN);
        return trialPeriod;
    }

    private Duration calculateTrialPeriodDuration(TrialPeriodCreateDTO request, Instant start) {
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
    @Override
    public void reject(Long trialPeriodId) {
        setConsensus(trialPeriodId, TrialState.REJECTED);
    }

    @Override
    public void approve(Long trialPeriodId) {
        setConsensus(trialPeriodId, TrialState.APPROVED);
    }

    private void setConsensus(Long trialPeriodId, TrialState state) {
        TrialPeriodTa trialPeriod = trialPeriodRepository.findById(trialPeriodId)
                .orElseThrow(() -> new EntityNotFoundException(trialPeriodId));

        if (trialPeriod.getState() != TrialState.PENDING) {
            throw new RuntimeException("Invalid state");
        }

        trialPeriod.setState(state);
        trialPeriodRepository.save(trialPeriod);

        LOG.info("Consensus of trial period of user {}: {}", trialPeriod.getUser().getId(), state);
        // TODO: notify
    }

    //-----------------------------------------------------------------------
    private void startTrialPeriodWorkflow(TrialPeriodTa entity) {
        runtimeService.createProcessInstanceByKey(ProcessVariables.TRIAL_PERIOD_DEFINITION_KEY)
                .setVariable(ProcessVariables.TRIAL_PERIOD_ID, entity.getId())
                .setVariable(ProcessVariables.TRIAL_PERIOD_END_DATE, Date.from(entity.getEnd()))
                .execute();
    }

    //-----------------------------------------------------------------------
    private TrialPeriodResponseDTO toResponseDto(TrialPeriodTa trialPeriod) {
        TrialPeriodResponseDTO dto = mapper.map(trialPeriod, TrialPeriodResponseDTO.class);
        dto.setEnd(trialPeriod.getStart().plus(trialPeriod.getDuration()));
        return dto;
    }

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
