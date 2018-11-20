package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.web.TrialPeriodCreateDTO;
import de.esports.aeq.admins.trials.web.TrialPeriodResponseDTO;

import java.util.List;

public interface TrialPeriodService {

    TrialPeriodConfigTa getConfiguration();

    void updateConfiguration(TrialPeriodConfigTa config);

    void createTrialPeriod(Long userId, TrialPeriodCreateDTO request);

    TrialPeriodResponseDTO findOne(Long trialPeriodId);

    List<TrialPeriodResponseDTO> findAll(Long userId);

    TrialPeriodTa update(TrialPeriodTa trialPeriod);

    void pending(Long trialPeriodId);

    void reject(Long trialPeriodId);

    void approve(Long trialPeriodId);
}
