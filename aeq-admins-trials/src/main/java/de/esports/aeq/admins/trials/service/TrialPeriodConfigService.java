package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;

public interface TrialPeriodConfigService {

    TrialPeriodConfigTa findActive();
}
