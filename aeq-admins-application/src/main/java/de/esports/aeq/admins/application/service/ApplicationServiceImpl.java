package de.esports.aeq.admins.application.service;

import de.esports.aeq.admins.application.domain.ApplicationTa;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;

import java.util.List;

import static de.esports.aeq.admins.application.service.ProcessVariables.APPLICATION_DEFINITION_KEY;
import static de.esports.aeq.admins.application.service.ProcessVariables.APPLICATION_ID;

@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {


    private ProcessEngine engine;

    @Override
    public List<String> getApprovalCandidateUsers() {
        return null;
    }

    private void startProcessApplicationTask(ApplicationTa application) {
        VariableMap map = Variables.createVariables()
                .putValue(APPLICATION_ID, application.getId());
        engine.getRuntimeService().startProcessInstanceById(APPLICATION_DEFINITION_KEY, map);
    }

    @Override
    public boolean requiresReview(Long applicationId) {
        /*
         * For now, all applications should require a review.
         * If requirements change, edit this place to dynamically determine whether review is
         * needed or not.
         */
        return true;
    }
}
