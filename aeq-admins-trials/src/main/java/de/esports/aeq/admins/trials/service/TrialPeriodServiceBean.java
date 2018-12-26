package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.common.BadRequestException;
import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.common.InternalServerErrorException;
import de.esports.aeq.admins.common.UpdateContext;
import de.esports.aeq.admins.configuration.SystemConfiguration;
import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.service.UserService;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.jpa.TrialPeriodRepository;
import de.esports.aeq.admins.trials.workflow.ProcessVariables;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static de.esports.aeq.admins.trials.Privileges.UPDATE_TRIAL_PERIOD;
import static de.esports.aeq.admins.trials.workflow.ProcessVariables.*;

@Service
public class TrialPeriodServiceBean implements TrialPeriodService {

    private static final Logger LOG = LoggerFactory.getLogger(TrialPeriodServiceBean.class);

    private final TrialPeriodRepository trialPeriodRepository;
    private final SystemConfiguration configuration;
    private final UserService userService;
    private final TrialPeriodMessaging messaging;
    private final RuntimeService runtimeService;
    private final ModelMapper mapper;

    @Autowired
    public TrialPeriodServiceBean(TrialPeriodRepository repository,
            SystemConfiguration configuration,
            UserService userService,
            TrialPeriodMessaging messaging,
            RuntimeService runtimeService,
            ModelMapper mapper) {
        this.trialPeriodRepository = repository;
        this.configuration = configuration;
        this.userService = userService;
        this.messaging = messaging;
        this.runtimeService = runtimeService;
        this.mapper = mapper;
    }

    //-----------------------------------------------------------------------
    @Override
    public List<TrialPeriod> findAll(Long userId) {
        return trialPeriodRepository.findAll().stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    //-----------------------------------------------------------------------
    @Override
    public TrialPeriod findOne(Long trialPeriodId) {
        TrialPeriodTa trialPeriod = findOneOrThrow(trialPeriodId);
        return mapper.map(trialPeriod, TrialPeriod.class);
    }

    //-----------------------------------------------------------------------
    @Override
    public void create(CreateTrialPeriod trialPeriod) {
        TrialPeriodTa entity = mapper.map(trialPeriod, TrialPeriodTa.class);
        UserTa user = userService.findById(trialPeriod.getUserId());
        entity.setUser(user);

        assertCreatePreconditions(entity);
        enrichTrialPeriod(entity);

        LOG.info("Creating trial period: {}", trialPeriod);
        TrialPeriodTa savedEntity = trialPeriodRepository.save(entity);

        createProcessInstance(savedEntity);
    }

    private void assertCreatePreconditions(TrialPeriodTa trialPeriod) {
        Long userId = trialPeriod.getUser().getId();
        var activeTrialPeriods = trialPeriodRepository.findAllActiveByUserId(userId);

        if (!activeTrialPeriods.isEmpty()) {
            TrialPeriod sample = map(activeTrialPeriods.iterator().next());
            throw new TrialPeriodAlreadyStartedException(sample);
        }

        trialPeriodRepository.findLatestByUserId(userId)
                .ifPresent(this::assertNoVestingPeriod);
    }

    private void assertNoVestingPeriod(TrialPeriodTa trialPeriod) {
        Period vestingPeriod = configuration.getVestingPeriod();
        if (vestingPeriod.isZero()) {
            return;
        }
        Instant now = Instant.now();
        Instant end = trialPeriod.getEnd().plus(vestingPeriod);
        if (now.isBefore(end)) {
            throw new TrialPeriodBlockedException(end);
        }
    }

    private void enrichTrialPeriod(TrialPeriodTa entity) {
        if (entity.getId() != null) {
            LOG.debug("Trial period {} has been initialized with an id that will be overridden.",
                    entity);
            entity.setId(null);
        }

        if (entity.getState() != TrialState.OPEN) {
            LOG.warn("Trial period {} has been initialized with a state other that {} and will " +
                    "be overridden.", entity, TrialState.OPEN);
        }
        // state must always be open
        entity.setState(TrialState.OPEN);

        if (entity.getStart() == null) {
            Instant now = Instant.now();
            LOG.debug("Trial period {} will be initialized with start time {}", entity, now);
            entity.setStart(now);
        }

        if (entity.getDuration() == null) {
            Duration defaultDuration = configuration.getTrialPeriodDuration();
            LOG.debug("Trial period {} will be initialized with default duration {}",
                    entity, defaultDuration);
            entity.setDuration(defaultDuration);
        }
    }

    private void createProcessInstance(TrialPeriodTa trialPeriod) {
        runtimeService.createProcessInstanceByKey(TRIAL_PERIOD_DEFINITION_KEY)
                .setVariable(ProcessVariables.TRIAL_PERIOD_ID, trialPeriod.getId())
                .setVariable(ProcessVariables.TRIAL_PERIOD_END_DATE,
                        Date.from(trialPeriod.getEnd()))
                .execute();
    }

    //-----------------------------------------------------------------------
    @Override
    public TrialPeriod update(UpdateTrialPeriod trialPeriod) {

        TrialPeriodTa existing = findOneOrThrow(trialPeriod.getId());
        TrialPeriodTa entity = mapper.map(trialPeriod, TrialPeriodTa.class);

        if (entity.equals(existing)) {
            LOG.debug("Trial period without changes will not be updated: {}", trialPeriod);
            return map(entity);
        }

        var context = UpdateContext.of(entity, entity);
        assertUpdatePreconditions(context);

        LOG.info("Updating trial period: {} -> {}", existing, trialPeriod);
        TrialPeriodTa savedEntity = trialPeriodRepository.save(entity);

        handleUpdate(context);
        return map(savedEntity);
    }

    private void assertUpdatePreconditions(UpdateContext<TrialPeriodTa> ctx) {
        assertValidStateTransition(ctx);
    }

    private void assertValidStateTransition(UpdateContext<TrialPeriodTa> ctx) {
        // if the state has not changed simply return
        if (ctx.getPrevious().getState() == ctx.getCurrent().getState()) {
            return;
        }
        var validStates = getSubsequentStatesForUser(ctx.getPrevious());
        if (validStates.contains(ctx.getCurrent().getState())) {
            throw new BadRequestException("Invalid state transition: " + ctx.getPrevious().getState() +
                    " -> " + ctx.getCurrent().getState());
        }
    }

    private void handleUpdate(UpdateContext<TrialPeriodTa> ctx) {
        // always process the state change first
        if (ctx.getPrevious().getState() != ctx.getCurrent().getState()) {
            handleStateChange(ctx);
        }
        if (!ctx.getPrevious().getDuration().equals(ctx.getCurrent().getDuration())) {
            updateProcessInstanceEnd(ctx.getCurrent().getId(), ctx.getCurrent().getEnd());
        }
    }

    private void updateProcessInstanceEnd(Long trialPeriodId, Instant end) {
        Execution instance = getProcessInstance(trialPeriodId);
        Date endDate = Date.from(end);
        runtimeService.setVariable(instance.getId(), TRIAL_PERIOD_END_DATE, endDate);
    }

    //-----------------------------------------------------------------------
    @Override
    public void delete(Long trialPeriodId) {
        TrialPeriodTa existing = findOneOrThrow(trialPeriodId);
        LOG.info("Deleting trial period: {}", existing);
        trialPeriodRepository.deleteById(trialPeriodId);
        handleDelete(existing);
    }

    private void handleDelete(TrialPeriodTa trialPeriod) {
        Execution instance = getProcessInstance(trialPeriod.getId());
        runtimeService.deleteProcessInstance(instance.getProcessInstanceId(),
                "Related trial period deleted.");
    }

    //-----------------------------------------------------------------------
    @Override
    public Collection<TrialState> getSubsequentStatesForUser(TrialPeriod trialPeriod) {
        TrialPeriodTa entity = mapper.map(trialPeriod, TrialPeriodTa.class);
        return getSubsequentStatesForUser(entity);
    }

    private Collection<TrialState> getSubsequentStatesForUser(TrialPeriodTa trialPeriod) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Collections.emptyList();
        }

        TrialState state = trialPeriod.getState();
        if (state == null || state.isTerminal()) {
            return Collections.emptyList();
        }

        GrantedAuthority authority = UPDATE_TRIAL_PERIOD.toGrantedAuthority();
        boolean hasPermission = authentication.getAuthorities().contains(authority);
        if (!hasPermission) {
            return Collections.emptyList();
        }

        var states = new HashSet<>(TrialState.getTerminalStates());

        if (state == TrialState.OPEN) {
            states.add(TrialState.PENDING);
        } else if (state == TrialState.PENDING) {
            states.add(TrialState.OPEN);
        } else {
            throw new InternalServerErrorException("Invalid state: " + state);
        }

        return states;
    }

    //-----------------------------------------------------------------------
    private void handleStateChange(UpdateContext<TrialPeriodTa> ctx) {
        TrialPeriodTa current = ctx.getCurrent();
        switch (current.getState()) {
            case OPEN:
                // TODO
                break;
            case PENDING:
                // TODO
                break;
            case APPROVED:
                setConsensus(current, current.getState());
                break;
            case REJECTED:
                setConsensus(current, current.getState());
                break;
            default:
        }

        TrialPeriod trialPeriod = map(current);
        messaging.notifyStateChanged(trialPeriod);
    }

    private void setConsensus(TrialPeriodTa entity, TrialState state) {
        if (entity.getState().isTerminal()) {
            throw new BadRequestException("Invalid state");
        }

        entity.setState(state);
        trialPeriodRepository.save(entity);

        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                .variableValueEquals(TRIAL_PERIOD_ID, entity)
                .singleResult();

        String convertedState = state.toString().toLowerCase();

        runtimeService.createMessageCorrelation(ProcessVariables.TRIAL_PERIOD_CONSENSUS_FOUND)
                .processInstanceId(instance.getId())
                .setVariable(ProcessVariables.TRIAL_PERIOD_CONSENSUS, convertedState);

        LOG.info("Consensus of trial period of user {}: {}", entity.getUser().getId(), state);
    }

    //-----------------------------------------------------------------------

    /*
     * Internally used utility methods.
     */

    private TrialPeriodTa findOneOrThrow(Long trialPeriodId) {
        return trialPeriodRepository.findById(trialPeriodId)
                .orElseThrow(() -> new EntityNotFoundException(trialPeriodId));
    }

    private Execution getProcessInstance(Long trialPeriodId) {
        return runtimeService.createExecutionQuery()
                .processDefinitionKey(TRIAL_PERIOD_DEFINITION_KEY)
                .variableValueEquals(TRIAL_PERIOD_ID, trialPeriodId)
                .singleResult();
    }

    private TrialPeriod map(TrialPeriodTa trialPeriod) {
        return mapper.map(trialPeriod, TrialPeriod.class);
    }
}
