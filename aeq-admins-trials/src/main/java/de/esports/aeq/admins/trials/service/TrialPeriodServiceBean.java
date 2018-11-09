package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.service.UserService;
import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.jpa.TrialPeriodRepository;
import de.esports.aeq.admins.trials.web.TrialPeriodCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TrialPeriodServiceBean implements TrialPeriodService {

    private UserService userService;
    private TrialPeriodRepository trialPeriodRepository;

    @Autowired
    public TrialPeriodServiceBean(UserService userService,
            TrialPeriodRepository trialPeriodRepository) {
        this.userService = userService;
        this.trialPeriodRepository = trialPeriodRepository;
    }

    //-----------------------------------------------------------------------
    @Override
    public void createTrialPeriod(TrialPeriodCreateDTO request) {
        // fail fast if no user is present
        UserTa user = userService.findById(request.getUserId());
        assertNoActiveTrialPeriodOrThrow(user.getId());

        TrialPeriodTa trialPeriod = new TrialPeriodTa();
        trialPeriod.setUser(user);

        Instant start = getTrialPeriodStart(request);
        trialPeriod.setStart(start);

        Duration duration = getTrialPeriodDuration(request, start);
        trialPeriod.setDuration(duration);

        trialPeriod.setState(TrialState.OPEN);
        TrialPeriodTa entity = trialPeriodRepository.save(trialPeriod);

        startTrialPeriodWorkflow(entity);
    }

    private Duration getTrialPeriodDuration(TrialPeriodCreateDTO request, Instant start) {
        if (request.getDuration().isPresent()) {
            // TODO: check permission
            return request.getDuration().get();
        }
        if (request.getEnd().isPresent()) {
            // TODO: check permission
            return Duration.between(start, request.getEnd().get());
        }
        return getConfiguration().getTrialPeriod();
    }

    private Instant getTrialPeriodStart(TrialPeriodCreateDTO request) {
        if (request.getStart().isPresent()) {
            // TODO: check permission
            return request.getStart().get().toInstant();
        }
        return Instant.now();
    }
    //-----------------------------------------------------------------------

    @Override
    public TrialPeriodConfigTa getConfiguration() {
        return null; // TODO
    }

    //-----------------------------------------------------------------------
    private void startTrialPeriodWorkflow(TrialPeriodTa entity) {
        // TODO: camunda
    }

    //-----------------------------------------------------------------------

    /**
     * Asserts that the user with the given id does not have any active trial period, otherwise an
     * exception is thrown.
     * <p>
     * A trial period is considered active, if its state is not terminal.
     *
     * @param userId the user id
     */
    private void assertNoActiveTrialPeriodOrThrow(Long userId) {
        trialPeriodRepository.findAllActive(userId)
                .stream().filter(TrialPeriodTa::isActive).findAny().ifPresent(entity -> {
            throw new TrialPeriodAlreadyStartedException(entity);
        });
    }
}
