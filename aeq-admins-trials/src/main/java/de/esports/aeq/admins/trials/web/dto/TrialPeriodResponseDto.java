package de.esports.aeq.admins.trials.web.dto;

import de.esports.aeq.admins.security.web.UserResponseDTO;
import de.esports.aeq.admins.trials.common.TrialState;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

public class TrialPeriodResponseDto implements Serializable {

    private Long id;

    private UserResponseDTO user;

    private TrialState state;

    private Instant start;

    private Duration duration;

    private Instant end;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public TrialState getState() {
        return state;
    }

    public void setState(TrialState state) {
        this.state = state;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }
}
