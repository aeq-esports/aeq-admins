package de.esports.aeq.admins.application.service;

import de.esports.aeq.admins.application.domain.ApplicationTa;

import java.util.Optional;

public interface ApplicationService {

    Optional<ApplicationTa> findOne(Long id);

    ApplicationTa create(ApplicationTa application);

    void approve(Long applicationId);

    void reject(Long applicationId);

    void validate(Long applicationId) throws ValidationException;
}
