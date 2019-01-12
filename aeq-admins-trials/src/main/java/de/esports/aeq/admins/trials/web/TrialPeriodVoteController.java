package de.esports.aeq.admins.trials.web;

import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.service.UserService;
import de.esports.aeq.admins.trials.service.TrialPeriodVoteService;
import de.esports.aeq.admins.trials.service.dto.CreateTrialPeriodVote;
import de.esports.aeq.admins.trials.service.dto.TrialPeriodVote;
import de.esports.aeq.admins.trials.service.dto.UpdateTrialPeriodVote;
import de.esports.aeq.admins.trials.web.dto.TrialPeriodVoteCreateDto;
import de.esports.aeq.admins.trials.web.dto.TrialPeriodVoteDto;
import de.esports.aeq.admins.trials.web.dto.TrialPeriodVotePatchDto;
import de.esports.aeq.admins.trials.web.dto.TrialPeriodVoteUpdateDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trials/{trialPeriodId}")
public class TrialPeriodVoteController {

    private final ModelMapper mapper;
    private final UserService userService;
    private final TrialPeriodVoteService voteService;

    public TrialPeriodVoteController(
            ModelMapper mapper, UserService userService,
            TrialPeriodVoteService voteService) {
        this.mapper = mapper;
        this.userService = userService;
        this.voteService = voteService;
    }

    @GetMapping
    @ResponseBody
    public Collection<TrialPeriodVoteDto> findAll(@PathVariable Long trialPeriodId) {
        return voteService.findAll(trialPeriodId).stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @GetMapping("/{voteId}")
    @ResponseBody
    public TrialPeriodVoteDto findOne(@PathVariable Long voteId) {
        TrialPeriodVote vote = voteService.findOne(voteId);
        return map(vote);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable Long trialPeriodId,
            @RequestBody @Valid TrialPeriodVoteCreateDto request,
            Principal principal) {
        // TODO make domain object for user
        UserTa user = userService.findByUsername(principal.getName());

        CreateTrialPeriodVote vote = new CreateTrialPeriodVote();
        vote.setTrialPeriodId(trialPeriodId);
        vote.setUserId(user.getId());
        mapper.map(request, vote);

        voteService.create(vote);
    }

    @PatchMapping("/{voteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Long voteId,
            @RequestBody @Valid TrialPeriodVotePatchDto request) {
        TrialPeriodVote vote = voteService.findOne(voteId);
        mapper.map(request, vote);
        update(vote);
    }

    @PutMapping("/{voteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long voteId,
            @RequestBody @Valid TrialPeriodVoteUpdateDto request) {
        TrialPeriodVote vote = voteService.findOne(voteId);
        mapper.map(request, vote);
        update(vote);
    }

    @DeleteMapping("/{trialPeriodVoteId}")
    public void delete(@PathVariable Long voteId) {
        voteService.delete(voteId);
    }

    //-----------------------------------------------------------------------

    private void update(TrialPeriodVote existing) {
        UpdateTrialPeriodVote update = new UpdateTrialPeriodVote();
        mapper.map(existing, update);
        voteService.update(update);
    }

    private TrialPeriodVoteDto map(TrialPeriodVote vote) {
        return mapper.map(vote, TrialPeriodVoteDto.class);
    }
}
