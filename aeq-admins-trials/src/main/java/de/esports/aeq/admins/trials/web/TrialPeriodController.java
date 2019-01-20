package de.esports.aeq.admins.trials.web;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.trials.service.TrialPeriodService;
import de.esports.aeq.admins.trials.service.dto.TrialPeriod;
import de.esports.aeq.admins.trials.service.dto.UpdateTrialPeriod;
import de.esports.aeq.admins.trials.web.dto.TrialPeriodCreateDto;
import de.esports.aeq.admins.trials.web.dto.TrialPeriodPatchDto;
import de.esports.aeq.admins.trials.web.dto.TrialPeriodResponseDto;
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
    public List<TrialPeriodResponseDto> findAll(@PathVariable Long userId) {
        return trialPeriodService.findAll(userId).stream()
                .map(trialPeriod -> mapper.map(trialPeriod, TrialPeriodResponseDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{trialPeriodId}")
    @ResponseBody
    public TrialPeriodResponseDto findOne(@PathVariable Long userId,
            @PathVariable Long trialPeriodId) {
        TrialPeriod trialPeriod = trialPeriodService.findOne(trialPeriodId);
        if (!trialPeriod.getUserId().equals(userId)) {
            throw new EntityNotFoundException(trialPeriodId);
        }
        return mapper.map(trialPeriod, TrialPeriodResponseDto.class);
    }

    //-----------------------------------------------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@PathVariable Long userId,
            @RequestBody TrialPeriodCreateDto request) {
        TrialPeriod trialPeriod = mapper.map(request, TrialPeriod.class);
        trialPeriod.setUserId(userId);
        trialPeriodService.create(trialPeriod);
    }

    @PatchMapping("/{trialPeriodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Long trialPeriodId,
            @RequestBody TrialPeriodPatchDto request) {
        TrialPeriod trialPeriod = trialPeriodService.findOne(trialPeriodId);
        mapper.map(request, trialPeriod);
        UpdateTrialPeriod update = mapper.map(trialPeriod, UpdateTrialPeriod.class);
        trialPeriodService.update(update);
    }

    @DeleteMapping("/{trialPeriodId}")
    public void delete(@PathVariable Long trialPeriodId) {
        trialPeriodService.delete(trialPeriodId);
    }
}
