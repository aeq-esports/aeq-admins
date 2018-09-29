package de.esports.aeq.admins.application.workflow;

import de.esports.aeq.admins.application.domain.ApplicationTa;
import de.esports.aeq.admins.application.service.ApplicationService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("approveTaskListener")
public class ApproveApplicationTaskListener implements TaskListener {

    private final ApplicationService service;

    @Autowired
    public ApproveApplicationTaskListener(ApplicationService service) {
        this.service = service;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        Long id = (Long) delegateTask.getVariable(ProcessVariables.APPLICATION_ID);
        ApplicationTa application = service.findOne(id).orElseThrow();
        ObjectValue value = Variables.objectValue(application).create();

        delegateTask.setPriority(40);
        delegateTask.setVariableLocal(ProcessVariables.TEXT, application.getText());

        System.out.println("VARS");
        delegateTask.getVariables().forEach((k, v) ->
                System.out.println("key: " + k + " value: " + v));
    }
}
