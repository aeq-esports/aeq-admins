package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.jpa.TrialPeriodConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrialPeriodConfigServiceBean implements TrialPeriodConfigService {

    private TrialPeriodConfigRepository repository;

    @Autowired
    public TrialPeriodConfigServiceBean(TrialPeriodConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    public TrialPeriodConfigTa findActive() {
        return repository.findAll().stream().findAny()
                .orElseThrow(EntityNotFoundException::new);
    }
}
