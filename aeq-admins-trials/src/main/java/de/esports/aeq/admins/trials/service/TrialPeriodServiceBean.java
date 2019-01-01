package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.common.BadRequestException;
import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.common.UpdateContext;
import de.esports.aeq.admins.configuration.SystemConfiguration;
import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.service.UserService;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.jpa.TrialPeriodRepository;
import de.esports.aeq.admins.trials.workflow.WorkflowController;
import org.camunda.bpm.engine.runtime.Execution;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrialPeriodServiceBean implements TrialPeriodService {

    private static final Logger LOG = LoggerFactory.getLogger(TrialPeriodServiceBean.class);

    private final TrialPeriodRepository trialPeriodRepository;
    private final SystemConfiguration configuration;
    private final UserService userService;
    private final WorkflowController workflow;

    private final ModelMapper mapper;

    @Autowired
    public TrialPeriodServiceBean(TrialPeriodRepository repository,
            SystemConfiguration configuration,
            UserService userService,
            WorkflowController workflow,
            ModelMapper mapper) {
        this.trialPeriodRepository = repository;
        this.configuration = configuration;
        this.userService = userService;
        this.workflow = workflow;
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
    public void create(TrialPeriod trialPeriod) {
        TrialPeriodTa entity = mapper.map(trialPeriod, TrialPeriodTa.class);

        // the user needs to be resolved before preconditions
        UserTa user = userService.findById(trialPeriod.getUserId());
        entity.setUser(user);

        assertCreatePreconditions(entity);
        enrichTrialPeriod(entity);

        LOG.info("Creating trial period: {}", trialPeriod);
        TrialPeriodTa savedEntity = trialPeriodRepository.save(entity);

        TrialPeriod result = map(savedEntity);
        workflow.createProcessInstance(result);
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

    //-----------------------------------------------------------------------

    @Override
    public TrialPeriod update(TrialPeriod trialPeriod) {
        TrialPeriodTa existing = findOneOrThrow(trialPeriod.getId());
        TrialPeriodTa entity = mapper.map(trialPeriod, TrialPeriodTa.class);

        var context = UpdateContext.of(existing, entity);
        assertUpdatePreconditions(context);

        LOG.info("Updating trial period: {} -> {}", existing, entity);
        TrialPeriodTa savedEntity = trialPeriodRepository.save(entity);

        handleUpdate(savedEntity, trialPeriod.getStateTransition());
        return map(savedEntity);
    }

    private void assertUpdatePreconditions(UpdateContext<TrialPeriodTa> ctx) {
        assertValidStateTransition(ctx);
    }

    private void assertValidStateTransition(UpdateContext<TrialPeriodTa> ctx) {
        // if the state has not changed simply return
        if (!hasStateChanged(ctx)) {
            return;
        }
        TrialPeriod trialPeriod = map(ctx.getPrevious());
        var validStates = getSubsequentStates(trialPeriod);
        if (!validStates.contains(ctx.getCurrent().getState())) {
            throw new BadRequestException("Invalid state transition: " + ctx.getPrevious().getState() +
                    " -> " + ctx.getCurrent().getState());
        }
    }

    private void handleUpdate(TrialPeriodTa entity,
            TrialStateTransition stateTransition) {
        // always process the state change first
        TrialPeriod trialPeriod = map(entity);
        workflow.updateProcessInstanceState(trialPeriod, stateTransition);

        workflow.updateProcessInstanceEnd(trialPeriod);
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
        Execution instance = workflow.getProcessInstance(trialPeriod.getId());
        // the process instance might have already finished
        // TODO: the archived version might also need to be deleted?
        if (instance == null) {
            LOG.info("Related process instance for trial period with id {} not found.",
                    trialPeriod.getId());
            return;
        }
        String reason = "Related trial period deleted.";
        workflow.deleteProcessInstance(instance.getProcessInstanceId(), reason);
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<TrialState> getSubsequentStates(TrialPeriod trialPeriod) {
        TrialState state = trialPeriod.getState();
        if (state == null || state.isTerminal()) {
            return Collections.emptyList();
        }

        var states = new HashSet<>(TrialState.getTerminalStates());

        if (state == TrialState.OPEN) {
            states.add(TrialState.PENDING);
        } else if (state == TrialState.PENDING) {
            states.add(TrialState.OPEN);
        }

        return states;
    }

    //-----------------------------------------------------------------------

    /*
     * Internally used utility methods.
     */

    private TrialPeriodTa findOneOrThrow(Long trialPeriodId) {
        return trialPeriodRepository.findById(trialPeriodId)
                .orElseThrow(() -> new EntityNotFoundException(trialPeriodId));
    }

    private TrialPeriod map(TrialPeriodTa trialPeriod) {
        return mapper.map(trialPeriod, TrialPeriod.class);
    }

    private boolean hasStateChanged(UpdateContext<TrialPeriodTa> ctx) {
        return ctx.getPrevious().getState() != ctx.getCurrent().getState();
    }

    private boolean hasDurationChanged(UpdateContext<TrialPeriodTa> ctx) {
        return !ctx.getPrevious().getDuration().equals(ctx.getCurrent().getDuration());
    }
}
