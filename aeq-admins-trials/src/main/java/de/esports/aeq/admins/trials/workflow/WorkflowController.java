package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.common.CamundaRelated;
import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("trialPeriodWorkflow")
public class WorkflowController {

    private final TrialPeriodService service;

    @Autowired
    public WorkflowController(TrialPeriodService service) {
        this.service = service;
    }

    @CamundaRelated
    public void pending(DelegateExecution execution) {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        service.pending(id);
    }

    @CamundaRelated
    public void approve(DelegateExecution execution) {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        service.approve(id);
    }

    @CamundaRelated
    public void reject(DelegateExecution execution) {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        service.reject(id);
    }

    @CamundaRelated
    public void extend(DelegateExecution execution) {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        service.extend(id);
    }
}
