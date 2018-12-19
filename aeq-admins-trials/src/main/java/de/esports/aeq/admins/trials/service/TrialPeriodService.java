package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface TrialPeriodService {

    TrialPeriodConfigTa getConfiguration();

    void updateConfiguration(TrialPeriodConfigTa config);

    @PreAuthorize("hasAuthority('CREATE_TRIAL_PERIOD')")
    void create(TrialPeriod trialPeriod);

    @PreAuthorize("hasAuthority('READ_TRIAL_PERIOD')")
    TrialPeriod findOne(Long trialPeriodId);

    @PreAuthorize("hasAuthority('READ_TRIAL_PERIOD')")
    List<TrialPeriod> findAll(Long userId);

    @PreAuthorize("hasAuthority('UPDATE_TRIAL_PERIOD')")
    TrialPeriod update(TrialPeriod trialPeriod);

    @PreAuthorize("hasAuthority('DELETE_TRIAL_PERIOD')")
    void delete(Long trialPeriodId);
}
