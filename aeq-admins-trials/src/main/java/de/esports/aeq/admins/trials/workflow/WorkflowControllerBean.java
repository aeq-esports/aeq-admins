package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.trials.service.dto.TrialPeriod;
import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

import static de.esports.aeq.admins.trials.workflow.ProcessVariables.*;

@Component
class WorkflowControllerBean implements WorkflowController {

    private final RuntimeService runtimeService;
    private final HistoryService historyService;

    @Autowired
    public WorkflowControllerBean(RuntimeService runtimeService,
            HistoryService historyService) {
        this.runtimeService = runtimeService;
        this.historyService = historyService;
    }

    @Override
    public Execution getProcessInstance(Long trialPeriodId) {
        Execution execution = runtimeService.createExecutionQuery()
                .processDefinitionKey(TRIAL_PERIOD_DEFINITION_KEY)
                .variableValueEquals(TRIAL_PERIOD_ID, trialPeriodId)
                .singleResult();

        if (execution == null) {
            throw new EntityNotFoundException(trialPeriodId);
        }
        return execution;
    }

    @Override
    public void createProcessInstance(TrialPeriod trialPeriod) {
        String stateString = trialPeriod.getState().toString().toLowerCase();
        runtimeService.createProcessInstanceByKey(TRIAL_PERIOD_DEFINITION_KEY)
                .setVariable(TRIAL_PERIOD_ID, trialPeriod.getId())
                .setVariable(TRIAL_PERIOD_END_DATE, Date.from(trialPeriod.getEnd()))
                .setVariable(TRIAL_PERIOD_STATE, stateString)
                .execute();
    }

    @Override
    public void updateProcessInstance(TrialPeriod trialPeriod) {
        Execution instance = getProcessInstance(trialPeriod.getId());

        String processInstanceId = instance.getProcessInstanceId();
        String stateString = trialPeriod.getState().toString().toLowerCase();

        if (trialPeriod.getState().isTerminal()) {
            sendTerminatingConsensusMessage(processInstanceId, stateString);
        } else {
            updateProcessInstanceStateNormal(processInstanceId, stateString);
        }

        // ??? TODO if null after update
        Date endDate = Date.from(trialPeriod.getEnd());
        runtimeService.setVariable(instance.getId(), TRIAL_PERIOD_END_DATE, endDate);
    }

    private void updateProcessInstanceStateNormal(String processInstanceId, String state) {
        runtimeService.setVariable(processInstanceId, TRIAL_PERIOD_STATE, state);
    }

    private void sendTerminatingConsensusMessage(String processInstanceId, String state) {
        runtimeService.createMessageCorrelation(TRIAL_PERIOD_TERMINATING_CONSENSUS)
                .processInstanceId(processInstanceId)
                .setVariable(TRIAL_PERIOD_STATE, state)
                .correlate();
    }

    @Override
    public void deleteProcessInstance(String processInstanceId, String reason) {
        try {
            runtimeService.deleteProcessInstance(processInstanceId, reason);
        } catch (BadUserRequestException e) {
            // ignore
        }
        historyService.deleteHistoricProcessInstance(processInstanceId);
    }

    @Override
    public void triggerVoteReceived(Long trialPeriodId) {
        Objects.requireNonNull(trialPeriodId);
        Execution instance = getProcessInstance(trialPeriodId);

        runtimeService.createMessageCorrelation(TRIAL_PERIOD_VOTE_RECEIVED)
                .processInstanceId(instance.getProcessInstanceId())
                .correlate();
    }

    //-----------------------------------------------------------------------

}
