package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RejectTrialPeriodService implements JavaDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(RejectTrialPeriodService.class);

    private TrialPeriodService service;

    @Autowired
    public RejectTrialPeriodService(TrialPeriodService service) {
        this.service = service;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long id = (Long) execution.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        LOG.info("Trial Period rejected: " + id);
    }
}
