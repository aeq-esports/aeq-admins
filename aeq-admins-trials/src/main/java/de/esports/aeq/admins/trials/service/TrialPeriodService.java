package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface TrialPeriodService {

    TrialPeriodConfigTa getConfiguration();

    void updateConfiguration(TrialPeriodConfigTa config);

    @PreAuthorize("hasAuthority('CREATE_TRIAL_PERIOD')")
    void create(TrialPeriodTa trialPeriod);

    @PreAuthorize("hasAuthority('READ_TRIAL_PERIOD')")
    TrialPeriodTa findOne(Long trialPeriodId);

    @PreAuthorize("hasAuthority('READ_TRIAL_PERIOD')")
    List<TrialPeriodTa> findAll(Long userId);

    @PreAuthorize("hasAuthority('UPDATE_TRIAL_PERIOD')")
    TrialPeriodTa update(TrialPeriodTa trialPeriod);

    @PreAuthorize("hasAuthority('DELETE_TRIAL_PERIOD')")
    void delete(Long trialPeriodId);

    void pending(Long trialPeriodId);

    void approve(Long trialPeriodId);

    void reject(Long trialPeriodId);

    void extend(Long trialPeriodId);
}
