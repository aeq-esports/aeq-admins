package de.esports.aeq.admins.application.workflow;

import de.esports.aeq.admins.application.domain.ApplicationTa;
import de.esports.aeq.admins.application.service.ApplicationService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("approveApplication")
public class ApproveApplicationTask implements JavaDelegate {

    private final ApplicationService service;
    private FormService formService;

    @Autowired
    public ApproveApplicationTask(ApplicationService service) {
        this.service = service;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long id = (Long) execution.getVariable(ProcessVariables.APPLICATION_ID);
        ApplicationTa application = service.findOne(id).orElseThrow();
        service.approve(id);
        //Map<String, Object> variables = Map.of("text", application.getText());
        //formService.submitTaskForm(execution.getId(), variables);
    }
}
