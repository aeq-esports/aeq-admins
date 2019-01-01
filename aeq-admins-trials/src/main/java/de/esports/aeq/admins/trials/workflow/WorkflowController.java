package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.common.InternalServerErrorException;
import de.esports.aeq.admins.trials.service.TrialPeriod;
import de.esports.aeq.admins.trials.service.TrialStateTransition;
import org.camunda.bpm.engine.runtime.Execution;

public interface WorkflowController {

    Execution getProcessInstance(Long trialPeriodId);

    void createProcessInstance(TrialPeriod trialPeriod);

    void updateProcessInstanceEnd(TrialPeriod trialPeriod);

    void updateProcessInstanceState(TrialPeriod trialPeriod, TrialStateTransition stateTransition);

    void deleteProcessInstance(String processInstanceId, String reason);
}
