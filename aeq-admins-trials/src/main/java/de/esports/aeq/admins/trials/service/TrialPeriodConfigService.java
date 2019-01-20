package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.service.dto.TrialPeriodConfig;
import org.springframework.stereotype.Service;

@Service
public interface TrialPeriodConfigService {

    TrialPeriodConfig getConfig();

    void updateConfig(TrialPeriodConfig config);
}
