package de.esports.aeq.admins.application.service;

import de.esports.aeq.admins.application.domain.ApplicationStatus;
import de.esports.aeq.admins.application.domain.ApplicationTa;
import de.esports.aeq.admins.application.jpa.ApplicationRepository;
import de.esports.aeq.admins.common.EntityNotFoundException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

import static de.esports.aeq.admins.application.workflow.ProcessVariables
        .APPLICATION_DEFINITION_KEY;
import static de.esports.aeq.admins.application.workflow.ProcessVariables.APPLICATION_ID;

@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository repository;
    private RuntimeService runtimeService;
    private TaskService taskService;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository repository, RuntimeService runtimeService,
            TaskService taskService) {
        this.repository = repository;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    @Override
    public ApplicationTa create(ApplicationTa application) {
        application.setStatus(ApplicationStatus.PENDING);
        ApplicationTa entity = repository.save(application);
        startApplicationTask(entity);
        return entity;
    }

    private void startApplicationTask(ApplicationTa application) {
        ProcessInstance instance = runtimeService.createProcessInstanceByKey
                (APPLICATION_DEFINITION_KEY)
                .setVariable(APPLICATION_ID, application.getId())
                .execute();

        List<Task> tasks = taskService.createTaskQuery().active().list();
        System.out.println(tasks.size());
        tasks.forEach(task -> System.out.println("AMOUNT OF TASKS" + task.getId()));
    }

    @Override
    public void approve(Long applicationId) {
        ApplicationTa application = repository.findById(applicationId)
                .orElseThrow(EntityNotFoundException::new);

        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new IllegalStateException("Application is already resolved.");
        }

        application.setStatus(ApplicationStatus.APPROVED);
        repository.save(application);
    }

    @Override
    public void reject(Long applicationId) {
        ApplicationTa application = repository.findById(applicationId)
                .orElseThrow(EntityNotFoundException::new);

        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new IllegalStateException("Application is already resolved.");
        }

        application.setStatus(ApplicationStatus.REJECTED);
        repository.save(application);

    }

    @Override
    public void validate(Long applicationId) throws ValidationException {
        // do nothing right now, all applications are valid
    }
}
