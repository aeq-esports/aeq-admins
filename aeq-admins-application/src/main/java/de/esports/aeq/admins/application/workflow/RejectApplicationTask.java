package de.esports.aeq.admins.application.workflow;

import de.esports.aeq.admins.application.service.ApplicationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rejectApplication")
public class RejectApplicationTask implements JavaDelegate {

    private final ApplicationService service;

    @Autowired
    public RejectApplicationTask(ApplicationService service) {
        this.service = service;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long id = (Long) execution.getVariable(ProcessVariables.APPLICATION_ID);
        service.reject(id);
    }
}
