package de.esports.aeq.admins.trials.web;

import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController("/users/{userId}/trial-periods")
public class TrialPeriodController {

    private final TrialPeriodService trialPeriodService;

    @Autowired
    public TrialPeriodController(TrialPeriodService trialPeriodService) {
        this.trialPeriodService = trialPeriodService;
    }

    //-----------------------------------------------------------------------
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@PathVariable Long userId,
            @RequestBody TrialPeriodCreateDTO dto,
            HttpServletRequest request) {
        trialPeriodService.createTrialPeriod(dto);
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
