package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.common.CamundaExpression;
import de.esports.aeq.admins.trials.common.TrialState;
import de.esports.aeq.admins.trials.service.TrialPeriodService;
import de.esports.aeq.admins.trials.service.TrialPeriodVoteService;
import de.esports.aeq.admins.trials.service.dto.TrialPeriod;
import de.esports.aeq.admins.trials.service.dto.UpdateTrialPeriod;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component("trialPeriodWorkflow")
public class ExpressionResolver {

    private static final Logger LOG = LoggerFactory.getLogger(ExpressionResolver.class);
    private final ModelMapper mapper;
    private final TrialPeriodService trialPeriodService;
    private final TrialPeriodVoteService trialPeriodVoteService;

    @Autowired
    public ExpressionResolver(ModelMapper mapper,
            TrialPeriodService trialPeriodService,
            TrialPeriodVoteService trialPeriodVoteService) {
        this.mapper = mapper;
        this.trialPeriodService = trialPeriodService;
        this.trialPeriodVoteService = trialPeriodVoteService;
    }

    @CamundaExpression
    public void pending(DelegateExecution execution) {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        TrialPeriod trialPeriod = trialPeriodService.findOne(id);
        trialPeriod.setState(TrialState.PENDING);
        UpdateTrialPeriod update = mapper.map(trialPeriod, UpdateTrialPeriod.class);
        trialPeriodService.update(update);
    }

    @CamundaExpression
    public void extend(DelegateExecution execution) {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        TrialPeriod trialPeriod = trialPeriodService.findOne(id);
        trialPeriod.setState(TrialState.OPEN);
        trialPeriod.setStart(Instant.now());
        UpdateTrialPeriod update = mapper.map(trialPeriod, UpdateTrialPeriod.class);
        trialPeriodService.update(update);
    }

    @CamundaExpression
    public void consensus(DelegateExecution execution) {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        TrialPeriod trialPeriod = trialPeriodService.findOne(id);

        String consensus = (String) execution.getVariable(ProcessVariables.TRIAL_PERIOD_STATE);
        TrialState state = TrialState.valueOf(consensus.toUpperCase());

        trialPeriod.setState(state);
        UpdateTrialPeriod update = mapper.map(trialPeriod, UpdateTrialPeriod.class);
        TrialPeriod entity = trialPeriodService.update(update);

        LOG.info("Consensus found for trial period: {}", entity);
    }

    @CamundaExpression
    public void evaluateVotes(DelegateExecution execution) {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        trialPeriodVoteService.evaluateVotes(id);
    }
}
