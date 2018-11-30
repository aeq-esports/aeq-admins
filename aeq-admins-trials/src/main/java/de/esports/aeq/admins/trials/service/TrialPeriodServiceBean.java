package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.common.BadRequestException;
import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.jpa.TrialPeriodConfigRepository;
import de.esports.aeq.admins.trials.jpa.TrialPeriodRepository;
import de.esports.aeq.admins.trials.workflow.ProcessVariables;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Service
public class TrialPeriodServiceBean implements TrialPeriodService {

    private static Logger LOG = LoggerFactory.getLogger(TrialPeriodServiceBean.class);

    private TrialPeriodRepository trialPeriodRepository;
    private TrialPeriodConfigRepository trialPeriodConfigRepository;
    private final Validator validator;
    private final RuntimeService runtimeService;

    @Autowired
    public TrialPeriodServiceBean(TrialPeriodRepository trialPeriodRepository,
            TrialPeriodConfigRepository trialPeriodConfigRepository,
            Validator validator, RuntimeService runtimeService) {
        this.trialPeriodRepository = trialPeriodRepository;
        this.trialPeriodConfigRepository = trialPeriodConfigRepository;
        this.validator = validator;
        this.runtimeService = runtimeService;
    }

    //-----------------------------------------------------------------------
    @Override
    public List<TrialPeriodTa> findAll(Long userId) {
        return trialPeriodRepository.findAll();
    }

    //-----------------------------------------------------------------------
    @Override
    public TrialPeriodTa findOne(Long trialPeriodId) {
        return findOneOrThrow(trialPeriodId);
    }

    //-----------------------------------------------------------------------
    @Override
    public void create(TrialPeriodTa trialPeriod) {
        validator.validate(trialPeriod); // TODO: add validation logic

        Long userId = trialPeriod.getUser().getId();
        assertNoActiveTrialPeriodOrThrow(userId);

        trialPeriod.setState(TrialState.OPEN);
        LOG.info("Creating trial period: {}", trialPeriod);
        TrialPeriodTa savedEntity = trialPeriodRepository.save(trialPeriod);
        startTrialPeriodWorkflow(savedEntity);
    }

    //-----------------------------------------------------------------------
    @Override
    public TrialPeriodTa update(TrialPeriodTa trialPeriod) {
        validator.validate(trialPeriod); // TODO: add validation logic
        TrialPeriodTa existing = findOneOrThrow(trialPeriod.getId());

        if (existing.equals(trialPeriod)) {
            return trialPeriod;
        }

        LOG.info("Updating trial period: {} -> {}", existing, trialPeriod);

        TrialPeriodTa savedEntity = trialPeriodRepository.save(trialPeriod);
        updateTrialPeriodWorkflow(savedEntity);
        return savedEntity;
    }

    //-----------------------------------------------------------------------
    @Override
    public void delete(Long trialPeriodId) {
        TrialPeriodTa existing = findOneOrThrow(trialPeriodId);
        LOG.info("Deleting trial period: {}", existing);
        trialPeriodRepository.deleteById(trialPeriodId);
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
    public void pending(Long trialPeriodId) {
        TrialPeriodTa trial = findOneOrThrow(trialPeriodId);
        if (trial.getState() != TrialState.OPEN) {
            throw new BadRequestException("Invalid state: " + trial.getState());
        }
        trial.setState(TrialState.PENDING);
        trialPeriodRepository.save(trial);
    }

    @Override
    public void approve(Long trialPeriodId) {
        setConsensus(trialPeriodId, TrialState.APPROVED);
    }

    @Override
    public void reject(Long trialPeriodId) {
        setConsensus(trialPeriodId, TrialState.REJECTED);
    }

    @Override
    public void extend(Long trialPeriodId) {
        TrialPeriodTa trial = findOneOrThrow(trialPeriodId);
        Duration duration = getConfiguration().getTrialPeriod();
        trial.extendDuration(duration);
        trialPeriodRepository.save(trial);
    }

    private void setConsensus(Long trialPeriodId, TrialState state) {
        TrialPeriodTa trialPeriod = findOneOrThrow(trialPeriodId);
        if (trialPeriod.getState().isTerminal()) {
            throw new BadRequestException("Invalid state");
        }

        trialPeriod.setState(state);
        trialPeriodRepository.save(trialPeriod);

        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                .variableValueEquals(ProcessVariables.TRIAL_PERIOD_ID, trialPeriod)
                .singleResult();

        String convertedState = state.toString().toLowerCase();

        runtimeService.createMessageCorrelation(ProcessVariables.TRIAL_PERIOD_CONSENSUS_FOUND)
                .processInstanceId(instance.getId())
                .setVariable(ProcessVariables.TRIAL_PERIOD_CONSENSUS, convertedState);

        LOG.info("Consensus of trial period of user {}: {}", trialPeriod.getUser().getId(), state);
    }

    //-----------------------------------------------------------------------
    private void startTrialPeriodWorkflow(TrialPeriodTa entity) {
        runtimeService.createProcessInstanceByKey(ProcessVariables.TRIAL_PERIOD_DEFINITION_KEY)
                .setVariable(ProcessVariables.TRIAL_PERIOD_ID, entity.getId())
                .setVariable(ProcessVariables.TRIAL_PERIOD_END_DATE, Date.from(entity.getEnd()))
                .execute();
    }

    private void updateTrialPeriodWorkflow(TrialPeriodTa trialPeriod) {
        // TODO
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
}
