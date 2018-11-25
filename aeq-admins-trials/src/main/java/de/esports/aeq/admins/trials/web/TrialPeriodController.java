package de.esports.aeq.admins.trials.web;

import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.service.UserService;
import de.esports.aeq.admins.trials.domain.TrialPeriodTa;
import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/trials")
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

    //-----------------------------------------------------------------------
    @GetMapping
    @ResponseBody
    public List<TrialPeriodResponseDTO> findAll(@PathVariable Long userId) {
        return trialPeriodService.findAll(userId).stream()
                .map(trialPeriod -> mapper.map(trialPeriod, TrialPeriodResponseDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{trialPeriodId}")
    @ResponseBody
    public TrialPeriodResponseDTO findOne(@PathVariable Long userId,
            @PathVariable Long trialPeriodId) {
        TrialPeriodTa trialPeriod = trialPeriodService.findOne(trialPeriodId);
        return mapper.map(trialPeriod, TrialPeriodResponseDTO.class);
    }

    //-----------------------------------------------------------------------
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@PathVariable Long userId,
            @RequestBody TrialPeriodCreateDTO request) {
        TrialPeriodTa trialPeriod = mapper.map(request, TrialPeriodTa.class);
        resolveUserId(trialPeriod, userId);
        trialPeriodService.create(trialPeriod);
    }

    @PatchMapping("/{trialPeriodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Long userId,
            @PathVariable Long trialPeriodId,
            @RequestBody TrialPeriodPatchDTO request) {
        TrialPeriodTa existing = trialPeriodService.findOne(trialPeriodId);
        mapper.map(request, existing);
        trialPeriodService.update(existing);
    }

    @PreAuthorize("@cse.canAccessRole(authentication, '#userId') and hasAuthority('')")
    @DeleteMapping("/{trialPeriodId}")
    public void delete(@PathVariable Long userId,
            @PathVariable Long trialPeriodId) {
        trialPeriodService.delete(trialPeriodId);
    }

    private void resolveUserId(TrialPeriodTa trialPeriod, Long userId) {
        UserTa user = userService.findById(userId);
        trialPeriod.setUser(user);
    }
}
