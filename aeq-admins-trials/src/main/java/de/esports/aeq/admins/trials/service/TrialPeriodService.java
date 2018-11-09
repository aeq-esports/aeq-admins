package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.web.TrialPeriodCreateDTO;

public interface TrialPeriodService {

    TrialPeriodConfigTa getConfiguration();

    void createTrialPeriod(TrialPeriodCreateDTO request);
}
