package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.trials.service.dto.TrialPeriod;
import org.camunda.bpm.engine.runtime.Execution;

public interface WorkflowController {

    Execution getProcessInstance(Long trialPeriodId);

    void createProcessInstance(TrialPeriod trialPeriod);

    void updateProcessInstanceEnd(TrialPeriod trialPeriod);

    void updateProcessInstanceState(TrialPeriod trialPeriod);

    void deleteProcessInstance(String processInstanceId, String reason);
}
