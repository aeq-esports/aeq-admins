package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.common.BadRequestException;
import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.common.UpdateContext;
import de.esports.aeq.admins.security.api.service.UserService;
import de.esports.aeq.admins.trials.common.TrialState;
import de.esports.aeq.admins.trials.exception.TrialPeriodAlreadyStartedException;
import de.esports.aeq.admins.trials.exception.TrialPeriodBlockedException;
import de.esports.aeq.admins.trials.jpa.TrialPeriodRepository;
import de.esports.aeq.admins.trials.jpa.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.service.dto.TrialPeriod;
import de.esports.aeq.admins.trials.service.dto.UpdateTrialPeriod;
import de.esports.aeq.admins.trials.workflow.WorkflowController;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static de.esports.aeq.admins.common.ExceptionResponseWrapper.notFound;

@Service
class TrialPeriodServiceBean implements TrialPeriodService {

    private static final Logger LOG = LoggerFactory.getLogger(TrialPeriodServiceBean.class);

    private final TrialPeriodRepository trialPeriodRepository;
    private final TrialPeriodConfigService configService;
    private final UserService userService;
    private final WorkflowController workflow;
    private final EntityManager entityManager;

    private final ModelMapper mapper;


    @Autowired
    public TrialPeriodServiceBean(TrialPeriodRepository repository,
            TrialPeriodConfigService configService,
            UserService userService,
            WorkflowController workflow,
            ModelMapper mapper,
            EntityManager entityManager) {
        this.trialPeriodRepository = repository;
        this.configService = configService;
        this.userService = userService;
        this.workflow = workflow;
        this.mapper = mapper;
        this.entityManager = entityManager;
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


        // the user needs to be resolved before preconditions
        Long userId = trialPeriod.getUserId();
        userService.findById(userId).orElseThrow(() -> notFound(userId));

        TrialPeriodTa entity = mapper.map(trialPeriod, TrialPeriodTa.class);
        entity.setUserId(userId);

        assertCreatePreconditions(userId);
        enrichTrialPeriod(entity);

        LOG.info("Creating trial period: {}", entity);
        TrialPeriodTa savedEntity = trialPeriodRepository.save(entity);

        TrialPeriod result = map(savedEntity);
        workflow.createProcessInstance(result);
    }

    private void assertCreatePreconditions(Long userId) {
        var activeTrialPeriods = trialPeriodRepository.findAllActiveByUserId(userId);

        if (!activeTrialPeriods.isEmpty()) {
            TrialPeriod sample = map(activeTrialPeriods.iterator().next());
            throw new TrialPeriodAlreadyStartedException(sample);
        }

        trialPeriodRepository.findLatestByUserId(userId)
                .ifPresent(this::assertNoVestingPeriod);
    }

    private void assertNoVestingPeriod(TrialPeriodTa trialPeriod) {
        Period vestingPeriod = configService.getConfig().getVestingPeriod();
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
            Duration defaultDuration = configService.getConfig().getTrialPeriodDuration();
            LOG.debug("Trial period {} will be initialized with default duration {}",
                    entity, defaultDuration);
            entity.setDuration(defaultDuration);
        }
    }

    //-----------------------------------------------------------------------

    @Override
    public TrialPeriod update(UpdateTrialPeriod trialPeriod) {
        TrialPeriodTa existing = findOneOrThrow(trialPeriod.getId());

        TrialPeriodTa entity = new TrialPeriodTa();
        mapper.map(existing, entity);
        mapper.map(trialPeriod, entity);

        var context = UpdateContext.of(existing, entity);
        assertUpdatePreconditions(context);

        LOG.info("Updating trial period: {} -> {}", existing, entity);
        TrialPeriodTa savedEntity = trialPeriodRepository.save(entity);

        TrialPeriod result = map(savedEntity);
        workflow.updateProcessInstance(result, trialPeriod.getTransition());
        return result;
    }

    private void assertUpdatePreconditions(UpdateContext<TrialPeriodTa> ctx) {
        assertValidStateTransition(ctx);
        getAmountOfExtensions(ctx.getCurrent().getId()); // TODO
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

    //-----------------------------------------------------------------------

    @Override
    public void delete(Long trialPeriodId) {
        TrialPeriodTa existing = findOneOrThrow(trialPeriodId);
        LOG.info("Deleting trial period: {}", existing);
        trialPeriodRepository.deleteById(trialPeriodId);
        handleDelete(existing);
    }

    private void handleDelete(TrialPeriodTa trialPeriod) {
        String reason = "Related trial period deleted.";
        workflow.deleteProcessInstance(trialPeriod.getId(), reason);
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

    private int getAmountOfExtensions(Long trialPeriodId) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<?> results = reader.createQuery()
                .forRevisionsOfEntityWithChanges(TrialPeriodTa.class, false)
                .add(AuditEntity.id().eq(trialPeriodId))
                .add(AuditEntity.revisionType().eq(RevisionType.MOD))
                .add(AuditEntity.property("state").hasChanged())
                .add(AuditEntity.property("state").eq(TrialState.OPEN))
                .getResultList();
        return results.size();
    }

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
