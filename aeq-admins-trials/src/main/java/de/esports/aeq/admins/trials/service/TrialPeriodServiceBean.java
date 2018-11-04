package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.service.UserService;
import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.jpa.TrialPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrialPeriodServiceBean implements TrialPeriodService {

    private UserService userService;
    private TrialPeriodConfigService trialPeriodConfigService;
    private TrialPeriodRepository trialPeriodRepository;

    @Autowired
    public TrialPeriodServiceBean(UserService userService,
            TrialPeriodConfigService trialPeriodConfigService,
            TrialPeriodRepository trialPeriodRepository) {
        this.userService = userService;
        this.trialPeriodConfigService = trialPeriodConfigService;
        this.trialPeriodRepository = trialPeriodRepository;
    }

    @Override
    public void createTrialPeriod(TrialPeriodTa trialPeriod) {
        UserTa user = userService.findById(trialPeriod);

        trialPeriodRepository.findAllActive(trialPeriod)
                .stream().findAny().ifPresent(trialPeriod -> {
            throw new TrialPeriodAlreadyStartedException(trialPeriod);
        });

        TrialPeriodTa trialPeriod = new TrialPeriodTa();
        trialPeriod.setUser(user);

        LocalDateTime start = LocalDateTime.now();
        trialPeriod.setStart(start);

        TrialPeriodConfigTa config = trialPeriodConfigService.findActive();
        LocalDateTime end = start.plus(config.getTrialPeriod());
        trialPeriod.setEnd(end);

        trialPeriodRepository.save(trialPeriod);
    }
}
