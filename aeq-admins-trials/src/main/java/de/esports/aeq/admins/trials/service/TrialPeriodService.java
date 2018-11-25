package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;

import java.util.List;

public interface TrialPeriodService {

    TrialPeriodConfigTa getConfiguration();

    void updateConfiguration(TrialPeriodConfigTa config);

    void create(TrialPeriodTa trialPeriod);

    TrialPeriodTa findOne(Long trialPeriodId);

    List<TrialPeriodTa> findAll(Long userId);

    TrialPeriodTa update(TrialPeriodTa trialPeriod);

    void delete(Long trialPeriodId);

    void pending(Long trialPeriodId);

    void approve(Long trialPeriodId);

    void reject(Long trialPeriodId);

    void extend(Long trialPeriodId);
}
