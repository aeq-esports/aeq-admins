package de.esports.aeq.admins.application.service;

import de.esports.aeq.admins.application.domain.ApplicationTa;

public interface ApplicationService {

    ApplicationTa create(ApplicationTa application);

    void approve(Long applicationId);

    void reject(Long applicationId);

    void validate(Long applicationId) throws ValidationException;
}
