package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.trials.common.TrialState;
import de.esports.aeq.admins.trials.common.TrialStateTransition;
import de.esports.aeq.admins.trials.service.dto.TrialPeriod;
import org.camunda.bpm.engine.runtime.Execution;

public interface WorkflowController {

    Execution getProcessInstance(Long trialPeriodId);

    void createProcessInstance(TrialPeriod trialPeriod);

    void updateProcessInstance(TrialPeriod trialPeriod, TrialStateTransition transition);

    void deleteProcessInstance(Long trialPeriodId, String reason);

    void triggerVoteReceived(Long trialPeriodId);

    void triggerTerminatingConsensus(Long trialPeriodId, TrialState terminal);
}
