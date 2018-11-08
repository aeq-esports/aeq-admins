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

    @Override
    public void createTrialPeriod(TrialPeriodCreateDTO request) {
        // fail fast if no user is present
        UserTa user = userService.findById(request.getUserId());

        trialPeriodRepository.findAllActive(user.getId())
                .stream().findAny().ifPresent(entity -> {
            throw new TrialPeriodAlreadyStartedException(entity);
        });

        TrialPeriodTa trialPeriod = new TrialPeriodTa();
        trialPeriod.setUser(user);

        if (request.getStart().isPresent()) {
            // TODO: check permission
            trialPeriod.setStart(request.getStart().get().toInstant());
        } else {
            trialPeriod.setStart(Instant.now());
        }

        Duration duration;
        if (request.getDuration().isPresent()) {
            // TODO: check permission
            duration = request.getDuration().get();
        } else if (request.getEnd().isPresent()) {
            // TODO: check permission
            duration = Duration.between(trialPeriod.getStart(), request.getEnd().get());
        } else {
            duration = getConfiguration().getTrialPeriod();
        }
        trialPeriod.setDuration(duration);

        trialPeriod.setState(TrialState.OPEN);
        trialPeriodRepository.save(trialPeriod);
    }

    @Override
    public void createTrialPeriodByUsername(String username) {

    }

    @Override
    public TrialPeriodConfigTa getConfiguration() {
        return null; // TODO
    }
}
