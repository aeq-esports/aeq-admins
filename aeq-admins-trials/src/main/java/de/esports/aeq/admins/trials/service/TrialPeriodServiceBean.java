package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.common.BadRequestException;
import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.common.InternalServerErrorException;
import de.esports.aeq.admins.common.UpdateContext;
import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.jpa.TrialPeriodConfigRepository;
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

import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

import static de.esports.aeq.admins.trials.Privileges.UPDATE_TRIAL_PERIOD;
import static de.esports.aeq.admins.trials.workflow.ProcessVariables.TRIAL_PERIOD_DEFINITION_KEY;
import static de.esports.aeq.admins.trials.workflow.ProcessVariables.TRIAL_PERIOD_ID;

@Service
public class TrialPeriodServiceBean implements TrialPeriodService {

    private static Logger LOG = LoggerFactory.getLogger(TrialPeriodServiceBean.class);

    private final TrialPeriodRepository trialPeriodRepository;
    private final TrialPeriodConfigRepository trialPeriodConfigRepository;
    private final TrialPeriodMessaging messaging;

    private final Validator validator;
    private final RuntimeService runtimeService;
    private final ModelMapper mapper;

    @Autowired
    public TrialPeriodServiceBean(TrialPeriodRepository repository,
            TrialPeriodConfigRepository configRepository,
            TrialPeriodMessaging messaging, Validator validator, RuntimeService runtimeService,
            ModelMapper mapper) {
        this.trialPeriodRepository = repository;
        this.trialPeriodConfigRepository = configRepository;
        this.messaging = messaging;
        this.validator = validator;
        this.runtimeService = runtimeService;
        this.mapper = mapper;
    }

    //-----------------------------------------------------------------------
    @Override
    public List<TrialPeriod> findAll(Long userId) {
        return trialPeriodRepository.findAll().stream()
                .map(this::mapToTrialPeriod)
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
        validator.validate(trialPeriod); // TODO: add validation logic

        Long userId = trialPeriod.getUser().getId();
        assertNoActiveTrialPeriodOrThrow(userId);

        trialPeriod.setState(TrialState.OPEN);
        LOG.info("Creating trial period: {}", trialPeriod);

        TrialPeriodTa entity = mapper.map(trialPeriod, TrialPeriodTa.class);
        TrialPeriodTa savedEntity = trialPeriodRepository.save(entity);

        handleCreate(savedEntity);
    }

    private void handleCreate(TrialPeriodTa trialPeriod) {
        runtimeService.createProcessInstanceByKey(TRIAL_PERIOD_DEFINITION_KEY)
                .setVariable(ProcessVariables.TRIAL_PERIOD_ID, trialPeriod.getId())
                .setVariable(ProcessVariables.TRIAL_PERIOD_END_DATE,
                        Date.from(trialPeriod.getEnd()))
                .execute();
    }

    //-----------------------------------------------------------------------
    @Override
    public TrialPeriod update(TrialPeriod trialPeriod) {
        validator.validate(trialPeriod); // TODO: add validation logic

        TrialPeriodTa existing = findOneOrThrow(trialPeriod.getId());
        LOG.info("Updating trial period: {} -> {}", existing, trialPeriod);

        TrialPeriodTa entity = mapper.map(trialPeriod, TrialPeriodTa.class);
        if (entity.equals(existing)) {
            return trialPeriod;
        }

        var context = UpdateContext.of(entity, entity);
        assertValidStateTransition(context);

        TrialPeriodTa savedEntity = trialPeriodRepository.save(entity);
        handleUpdate(context);
        return mapToTrialPeriod(savedEntity);
    }

    private void assertValidStateTransition(UpdateContext<TrialPeriodTa> ctx) {
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
        if (ctx.getPrevious().getState() != ctx.getCurrent().getState()) {
            handleStateChange(ctx);
        }
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
        Execution instance = runtimeService.createExecutionQuery()
                .processDefinitionKey(TRIAL_PERIOD_DEFINITION_KEY)
                .variableValueEquals(TRIAL_PERIOD_ID, trialPeriod.getId())
                .singleResult();

        runtimeService.deleteProcessInstance(instance.getProcessInstanceId(),
                "Related trial period deleted.");
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

        TrialPeriod trialPeriod = mapToTrialPeriod(current);
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
    private TrialPeriodTa findOneOrThrow(Long trialPeriodId) {
        return trialPeriodRepository.findById(trialPeriodId)
                .orElseThrow(() -> new EntityNotFoundException(trialPeriodId));
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

    private TrialPeriod mapToTrialPeriod(TrialPeriodTa trialPeriod) {
        return mapper.map(trialPeriod, TrialPeriod.class);
    }
}
