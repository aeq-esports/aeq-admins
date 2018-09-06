package de.esports.aeq.admins.application.workflow;

import de.esports.aeq.admins.application.service.ApplicationService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
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
        delegateTask.setPriority(40);
    }
}
