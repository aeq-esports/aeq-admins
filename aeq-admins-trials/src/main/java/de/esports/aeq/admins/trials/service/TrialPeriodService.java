package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.web.TrialPeriodCreateDTO;
import de.esports.aeq.admins.trials.web.TrialPeriodResponseDTO;
import de.esports.aeq.admins.trials.web.TrialPeriodUpdateDto;

import java.util.List;

public interface TrialPeriodService {

    //-----------------------------------------------------------------------
    TrialPeriodTa update(TrialPeriodUpdateDto request);

    TrialPeriodConfigTa getConfiguration();

    void updateConfiguration(TrialPeriodConfigTa config);

    void createTrialPeriod(Long userId, TrialPeriodCreateDTO request);

    TrialPeriodResponseDTO findOne(Long trialPeriodId);

    List<TrialPeriodResponseDTO> findAll(Long userId);

    void pending(Long trialPeriodId);

    void approve(Long trialPeriodId);

    void reject(Long trialPeriodId);

    void extend(Long trialPeriodId);
}
