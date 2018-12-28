package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.common.InternalServerErrorException;
import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.service.TrialPeriod;
import de.esports.aeq.admins.trials.service.TrialStateTransition;
import org.camunda.bpm.engine.runtime.Execution;

import java.time.Instant;

public interface WorkflowController {

    Execution getProcessInstance(Long trialPeriodId);

    default Execution getProcessInstanceOrThrow(Long trialPeriodId) {
        Execution execution = getProcessInstance(trialPeriodId);
        if (execution == null) {
            throw new InternalServerErrorException("Related process instance not found for trial " +
                    "period with id:" + trialPeriodId);
        }
        return execution;
    }

    void createProcessInstance(TrialPeriod trialPeriod);

    void updateProcessInstanceEnd(Long trialPeriodId, Instant end);

    void updateProcessInstanceState(Long trialPeriodId, TrialState state,
            TrialStateTransition stateTransition);

    void deleteProcessInstance(String processInstanceId, String reason);
}
