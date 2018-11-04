package de.esports.aeq.admins.trials.web;

import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.service.UserService;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController("/users/{userId}/trial-periods")
public class TrialPeriodController {

    private final ModelMapper mapper;
    private final UserService userService;
    private final TrialPeriodService trialPeriodService;

    @Autowired
    public TrialPeriodController(ModelMapper mapper,
            UserService userService,
            TrialPeriodService trialPeriodService) {
        this.mapper = mapper;
        this.userService = userService;
        this.trialPeriodService = trialPeriodService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@PathVariable Long userId,
            @RequestBody TrialPeriodCreateDTO request) {
        TrialPeriodTa trialPeriod = mapper.map(request, TrialPeriodTa.class);
        UserTa user = userService.findById(userId);
        trialPeriod.setUser(user);

        trialPeriodService.createTrialPeriod(trialPeriod);
    }

    @PatchMapping("/{trialPeriodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Long userId,
            @PathVariable Long trialPeriodId,
            @RequestBody TrialPeriodPatchDTO request) {

    }

    @DeleteMapping("/{trialPeriodId}")
    public void delete(@PathVariable Long userId,
            @PathVariable Long trialPeriodId,
            Authentication authentication) {
        // check permissions
    }
}
