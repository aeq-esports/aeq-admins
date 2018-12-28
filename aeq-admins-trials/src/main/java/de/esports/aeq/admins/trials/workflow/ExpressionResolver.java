package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.common.CamundaRelated;
import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.service.TrialPeriod;
import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component("trialPeriodWorkflow")
public class ExpressionResolver {

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
    public void approve(DelegateExecution execution) {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        TrialPeriod trialPeriod = service.findOne(id);
        trialPeriod.setState(TrialState.APPROVED);
        service.update(trialPeriod);
    }

    @CamundaRelated
    public void reject(DelegateExecution execution) {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        TrialPeriod trialPeriod = service.findOne(id);
        trialPeriod.setState(TrialState.REJECTED);
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


}
