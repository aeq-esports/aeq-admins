package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.common.CamundaRelated;
import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.service.TrialPeriod;
import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component("trialPeriodWorkflow")
public class ExpressionResolver {

    private static final Logger LOG = LoggerFactory.getLogger(ExpressionResolver.class);
    private final TrialPeriodService service;

    @Autowired
    public ExpressionResolver(TrialPeriodService service) {
        this.service = service;
    }

    @CamundaRelated
    public void pending(DelegateExecution execution) {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        TrialPeriod trialPeriod = service.findOne(id);
        trialPeriod.setState(TrialState.PENDING);
        service.update(trialPeriod);
    }

    @CamundaRelated
    public void extend(DelegateExecution execution) {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        TrialPeriod trialPeriod = service.findOne(id);
        trialPeriod.setState(TrialState.OPEN);
        trialPeriod.setStart(Instant.now());
        service.update(trialPeriod);
    }

    @CamundaRelated
    public void consensus(DelegateExecution execution) {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        TrialPeriod trialPeriod = service.findOne(id);

        String consensus = (String) execution.getVariable(ProcessVariables.TRIAL_PERIOD_STATE);
        TrialState state = TrialState.valueOf(consensus.toUpperCase());

        trialPeriod.setState(state);
        TrialPeriod entity = service.update(trialPeriod);

        LOG.info("Consensus found for trial period: {}", entity);
    }
}
