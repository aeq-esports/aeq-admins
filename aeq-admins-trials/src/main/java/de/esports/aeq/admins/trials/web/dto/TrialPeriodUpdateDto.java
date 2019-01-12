package de.esports.aeq.admins.trials.web.dto;

import de.esports.aeq.admins.trials.TrialsConfig;
import de.esports.aeq.admins.trials.common.TrialState;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;

public class TrialPeriodUpdateDto {

    @NotNull
    private Long id;

    @NotNull
    private ZonedDateTime start;

    /**
     * The duration of the trial period.
     * <p>
     * May be <code>null</code> in which case the default period will be used. The default is
     * configured in the {@link TrialsConfig}.
     */
    @Nullable
    private Duration duration;

    /**
     * The end of the trial period.
     * <p>
     * May be used as an alternative for the <code>duration</code> (if both are configured the
     * duration will be preferred). The zone is required since clients might send the time in their
     * local time zone. This time should be converted to UTC afterwards.
     */
    @Nullable
    private ZonedDateTime end;

    @NotNull
    private TrialState state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public Optional<Duration> getDuration() {
        return Optional.ofNullable(duration);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Optional<ZonedDateTime> getEnd() {
        return Optional.ofNullable(end);
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public TrialState getState() {
        return state;
    }

    public void setState(TrialState state) {
        this.state = state;
    }
}
