package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.common.TrialState;
import de.esports.aeq.admins.trials.service.dto.TrialPeriod;
import de.esports.aeq.admins.trials.service.dto.UpdateTrialPeriod;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;
import java.util.List;

public interface TrialPeriodService {

    @PreAuthorize("hasAuthority('CREATE_TRIAL_PERIOD') or " +
            "(hasAuthority('CREATE_OWN_TRIAL_PERIOD') and @cse.hasUserId(#trialPeriod.userId))")
    void create(TrialPeriod trialPeriod);

    @PreAuthorize("hasAuthority('READ_TRIAL_PERIOD') or" +
            "(hasAuthority('READ_OWN_TRIAL_PERIOD') and @cse.hasUserId(#trialPeriodId))")
    TrialPeriod findOne(Long trialPeriodId);

    @PreAuthorize("hasAuthority('READ_TRIAL_PERIOD')")
    List<TrialPeriod> findAll(Long userId);

    @PreAuthorize("hasAuthority('UPDATE_TRIAL_PERIOD')")
    TrialPeriod update(UpdateTrialPeriod trialPeriod);

    @PreAuthorize("hasAuthority('DELETE_TRIAL_PERIOD')")
    void delete(Long trialPeriodId);

    /**
     * Returns possible subsequent states of this trial period.
     * <p>
     * If no subsequent state are available, the state of this trial period cannot be changed.
     *
     * @param trialPeriod the trial period
     * @return a {@link Collection} of subsequent states or an empty {@link Collection} if no
     * subsequent states are available
     */
    @PreAuthorize("hasAuthority('READ_TRIAL_PERIOD')")
    Collection<TrialState> getSubsequentStates(TrialPeriod trialPeriod);
}
