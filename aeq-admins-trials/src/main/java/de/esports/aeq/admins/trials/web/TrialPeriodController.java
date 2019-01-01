package de.esports.aeq.admins.trials.web;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.trials.service.TrialPeriod;
import de.esports.aeq.admins.trials.service.TrialPeriodService;
import de.esports.aeq.admins.trials.service.TrialStateTransition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/trials")
public class TrialPeriodController {

    private final ModelMapper mapper;
    private final TrialPeriodService trialPeriodService;

    @Autowired
    public TrialPeriodController(ModelMapper mapper,
            TrialPeriodService trialPeriodService) {
        this.mapper = mapper;
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
        TrialPeriod trialPeriod = trialPeriodService.findOne(trialPeriodId);
        if (!trialPeriod.getUserId().equals(userId)) {
            throw new EntityNotFoundException(trialPeriodId);
        }
        return mapper.map(trialPeriod, TrialPeriodResponseDTO.class);
    }

    //-----------------------------------------------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@PathVariable Long userId,
            @RequestBody TrialPeriodCreateDTO request) {
        TrialPeriod trialPeriod = mapper.map(request, TrialPeriod.class);
        trialPeriod.setUserId(userId);
        trialPeriodService.create(trialPeriod);
    }

    @PatchMapping("/{trialPeriodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Long trialPeriodId,
            @RequestBody TrialPeriodPatchDTO request) {
        TrialPeriod trialPeriod = trialPeriodService.findOne(trialPeriodId);
        trialPeriod.setId(trialPeriodId);
        trialPeriod.setStateTransition(TrialStateTransition.TERMINATED);
        mapper.map(request, trialPeriod);
        trialPeriodService.update(trialPeriod);
    }

    @DeleteMapping("/{trialPeriodId}")
    public void delete(@PathVariable Long trialPeriodId) {
        trialPeriodService.delete(trialPeriodId);
    }
}
