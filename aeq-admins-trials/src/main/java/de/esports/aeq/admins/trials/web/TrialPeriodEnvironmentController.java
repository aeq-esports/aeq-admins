package de.esports.aeq.admins.trials.web;

import de.esports.aeq.admins.trials.service.TrialPeriodConfigService;
import de.esports.aeq.admins.trials.service.dto.TrialPeriodConfig;
import de.esports.aeq.admins.trials.web.dto.TrialPeriodConfigDto;
import de.esports.aeq.admins.trials.web.dto.TrialPeriodConfigPatchDto;
import de.esports.aeq.admins.trials.web.dto.TrialPeriodConfigUpdateDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/trials/config")
public class TrialPeriodEnvironmentController {

    private final ModelMapper mapper;
    private final TrialPeriodConfigService service;

    public TrialPeriodEnvironmentController(ModelMapper mapper,
            TrialPeriodConfigService service) {
        this.mapper = mapper;
        this.service = service;
    }

    //-----------------------------------------------------------------------

    @GetMapping
    @ResponseBody
    public TrialPeriodConfigDto findOne() {
        TrialPeriodConfig config = service.getConfig();
        return mapper.map(config, TrialPeriodConfigDto.class);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@Valid @RequestBody TrialPeriodConfigPatchDto request) {
        TrialPeriodConfig config = mapper.map(request, TrialPeriodConfig.class);
        service.updateConfig(config);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody TrialPeriodConfigUpdateDto request) {
        TrialPeriodConfig config = mapper.map(request, TrialPeriodConfig.class);
        service.updateConfig(config);
    }
}
