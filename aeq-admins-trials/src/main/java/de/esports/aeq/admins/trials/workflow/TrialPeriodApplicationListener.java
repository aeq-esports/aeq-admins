package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrialPeriodApplicationListener {

    private TrialPeriodService service;

    @Autowired
    public TrialPeriodApplicationListener(TrialPeriodService service) {
        this.service = service;
    }

    public void rejectTrialPeriod(DelegateTask delegate) {
        Long id = (Long) delegate.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        updateTrialPeriodState(id, TrialState.REJECTED);
    }

    public void acceptTrialPeriod(DelegateTask delegate) {
        Long id = (Long) delegate.getVariable(ProcessVariables.TRIAL_PERIOD_ID);
        updateTrialPeriodState(id, TrialState.ACCEPTED);
    }

    private void updateTrialPeriodState(Long id, TrialState state) {
        TrialPeriodTa trialPeriod = service.findOne(id);
        trialPeriod.setState(state);
        service.update(trialPeriod);
    }
}
