package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.trials.domain.TrialPeriodVoteTa;
import de.esports.aeq.admins.trials.domain.TrialState;
import de.esports.aeq.admins.trials.exception.AlreadyVotedException;
import de.esports.aeq.admins.trials.exception.IllegalTrialPeriodStateException;
import de.esports.aeq.admins.trials.jpa.TrialPeriodVoteRepository;
import de.esports.aeq.admins.trials.service.dto.CreateTrialPeriodVote;
import de.esports.aeq.admins.trials.service.dto.TrialPeriod;
import de.esports.aeq.admins.trials.service.dto.TrialPeriodVote;
import de.esports.aeq.admins.trials.service.dto.UpdateTrialPeriodVote;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class TrialPeriodVoteServiceBean implements TrialPeriodVoteService {

    private final ModelMapper mapper;
    private final TrialPeriodVoteRepository repository;
    private final TrialPeriodService trialPeriodService;
    private final TrialPeriodVoteEvaluator evaluator;

    public TrialPeriodVoteServiceBean(
            ModelMapper mapper, TrialPeriodVoteRepository repository,
            TrialPeriodService trialPeriodService,
            TrialPeriodVoteEvaluator evaluator) {
        this.mapper = mapper;
        this.repository = repository;
        this.trialPeriodService = trialPeriodService;
        this.evaluator = evaluator;
    }

    @Override
    public TrialPeriodVote create(CreateTrialPeriodVote vote) {
        assertValidTrialPeriod(vote.getTrialPeriodId());
        assertNotAlreadyVoted(vote.getUserId());

        TrialPeriodVoteTa entity = mapper.map(vote, TrialPeriodVoteTa.class);
        entity = repository.save(entity);

        Long trialPeriodId = entity.getTrialPeriod().getId();
        evaluator.evaluate(trialPeriodId);

        return map(entity);
    }

    @Override
    public TrialPeriodVote findOne(Long voteId) {
        return map(findOneOrThrow(voteId));
    }

    @Override
    public TrialPeriodVote findOneByUserId(Long userId) {
        TrialPeriodVoteTa entity = findOneByUserIdOrThrow(userId);
        return map(entity);
    }

    @Override
    public Collection<TrialPeriodVote> findAll(Long trialPeriodId) {
        return repository.findAllByTrialPeriodId(trialPeriodId).stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public TrialPeriodVote update(UpdateTrialPeriodVote vote) {
        TrialPeriodVoteTa entity = mapper.map(vote, TrialPeriodVoteTa.class);

        // merge with existing entity
        TrialPeriodVoteTa existing = findOneOrThrow(entity.getId());
        mapper.map(entity, existing);

        entity = repository.save(existing);
        Long trialPeriodId = entity.getTrialPeriod().getId();
        evaluator.evaluate(trialPeriodId);

        return map(entity);
    }

    @Override
    public void delete(Long voteId) {
        repository.deleteById(voteId);
    }

    //-----------------------------------------------------------------------

    private TrialPeriodVote map(TrialPeriodVoteTa vote) {
        return mapper.map(vote, TrialPeriodVote.class);
    }

    private TrialPeriodVoteTa findOneOrThrow(Long voteId) {
        return repository.findById(voteId)
                .orElseThrow(EntityNotFoundException::new);
    }

    private TrialPeriodVoteTa findOneByUserIdOrThrow(Long userId) {
        return repository.findByUserId(userId)
                .orElseThrow(EntityNotFoundException::new);
    }

    private void assertValidTrialPeriod(Long trialPeriodId) {
        TrialPeriod trialPeriod = trialPeriodService.findOne(trialPeriodId);
        TrialState state = trialPeriod.getState();
        if (state.isTerminal()) {
            throw new IllegalTrialPeriodStateException(state);
        }
    }

    private void assertNotAlreadyVoted(Long userId) {
        repository.findByUserId(userId).ifPresent(e -> {
            throw new AlreadyVotedException();
        });
    }
}
