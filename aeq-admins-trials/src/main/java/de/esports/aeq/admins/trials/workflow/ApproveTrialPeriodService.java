package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApproveTrialPeriodService implements JavaDelegate {

    private TrialPeriodService service;

    @Autowired
    public ApproveTrialPeriodService(TrialPeriodService service) {
        this.service = service;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        service.approve(id);
    }
}