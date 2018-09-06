package de.esports.aeq.admins.application.workflow;

import de.esports.aeq.admins.application.service.ApplicationService;
import de.esports.aeq.admins.application.service.ValidationException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("validateApplication")
public class ValidateApplicationTask implements JavaDelegate {

    private final ApplicationService service;

    @Autowired
    public ValidateApplicationTask(ApplicationService service) {
        this.service = service;
    }

    @Override
    public void execute(DelegateExecution execution) {
        Long applicationId = (Long) execution.getVariable(ProcessVariables.APPLICATION_ID);
        try {
            service.validate(applicationId);
            execution.setVariable(ProcessVariables.VALID, true);

            execution.setVariable(ProcessVariables.REQUIRES_APPROVAL, true);
        } catch (ValidationException e) {
            execution.setVariable(ProcessVariables.VALID, false);
        }
    }
}
