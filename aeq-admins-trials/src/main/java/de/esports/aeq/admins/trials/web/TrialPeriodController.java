package de.esports.aeq.admins.trials.web;

import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/trials")
public class TrialPeriodController {

    private final TrialPeriodService trialPeriodService;

    @Autowired
    public TrialPeriodController(TrialPeriodService trialPeriodService) {
        this.trialPeriodService = trialPeriodService;
    }

    //-----------------------------------------------------------------------
    @GetMapping
    @ResponseBody
    public List<TrialPeriodResponseDTO> findAll(@PathVariable Long userId) {
        return trialPeriodService.findAll(userId);
    }

    //-----------------------------------------------------------------------
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@PathVariable Long userId,
            @RequestBody TrialPeriodCreateDTO dto) {
        trialPeriodService.createTrialPeriod(userId, dto);
    }

    @PatchMapping("/{trialId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Long userId,
            @PathVariable Long trialId,
            @RequestBody TrialPeriodPatchDTO request) {

    }

    @PreAuthorize("@cse.canAccessRole(authentication, '#userId') and hasAuthority('')")
    @DeleteMapping("/{trialPeriodId}")
    public void delete(@PathVariable Long userId,
            @PathVariable Long trialPeriodId) {
        // check permissions
    }
}
