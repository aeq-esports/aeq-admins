package de.esports.aeq.admins.trials.web;

import de.esports.aeq.admins.trials.TrialsConfig;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;

public class TrialPeriodCreateDTO implements Serializable {

    /**
     * The start of the trial period.
     * <p>
     * May be <code>null</code> in which case the current time will be used. The zone is required
     * since clients might send the time in their local time zone. This time should be converted to
     * UTC afterwards.
     */
    @Nullable
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
     * May be used as an alternative for the <code>duration</code> (both cannot be used at the same
     * time). The zone is required since clients might send the time in their local time zone. This
     * time should be converted to UTC afterwards.
     */
    @Nullable
    private ZonedDateTime end;

    @Nullable
    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(@Nullable ZonedDateTime start) {
        this.start = start;
    }

    @Nullable
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(@Nullable Duration duration) {
        this.duration = duration;
    }

    @Nullable
    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(@Nullable ZonedDateTime end) {
        this.end = end;
    }
}
