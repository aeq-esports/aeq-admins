package de.esports.aeq.admins.trials.web;

import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.service.UserService;
import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;

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
            @RequestBody TrialPeriodCreateDTO dto,
            HttpServletRequest request) {
        TrialPeriodTa trialPeriod = new TrialPeriodTa();

        TrialPeriodConfigTa config = trialPeriodService.getConfiguration();
        Instant start = dto.getStart()
                .map(ZonedDateTime::toInstant)
                .orElse(Instant.now());
        trialPeriod.setStart(start);

        if (dto.getEnd().isPresent()) {
            trialPeriod.setDuration(Duration.between(start, dto.getEnd().get().toInstant()));
        } else {
            trialPeriod.setDuration(dto.getDuration().orElse(config.getTrialPeriod()));
        }

        trialPeriod.setState(TrialState.OPEN);

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

    @PreAuthorize("@cse.canAccessRole(authentication, '#userId') and hasAuthority('')")
    @DeleteMapping("/{trialPeriodId}")
    public void delete(@PathVariable Long userId,
            @PathVariable Long trialPeriodId) {
        // check permissions
    }
}
