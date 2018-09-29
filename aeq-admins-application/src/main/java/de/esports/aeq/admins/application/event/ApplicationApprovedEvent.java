package de.esports.aeq.admins.application.event;

import de.esports.aeq.admins.application.domain.ApplicationTa;

public class ApplicationApprovedEvent {

    private final ApplicationTa application;

    public ApplicationApprovedEvent(ApplicationTa application) {
        this.application = application;
    }

    public ApplicationTa getApplication() {
        return application;
    }
}
