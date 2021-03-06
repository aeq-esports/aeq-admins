package de.esports.aeq.admins.trials.web.dto;

import java.io.Serializable;
import java.time.Duration;
import java.time.Period;
import java.util.Objects;
import java.util.StringJoiner;

public class TrialPeriodConfigPatchDto implements Serializable {

    private Duration trialPeriodDuration;
    private Period vestingPeriod;
    private Integer requiredVotes;
    private Double requiredVoteMajority;

    public Duration getTrialPeriodDuration() {
        return trialPeriodDuration;
    }

    public void setTrialPeriodDuration(Duration trialPeriodDuration) {
        this.trialPeriodDuration = trialPeriodDuration;
    }

    public Period getVestingPeriod() {
        return vestingPeriod;
    }

    public void setVestingPeriod(Period vestingPeriod) {
        this.vestingPeriod = vestingPeriod;
    }

    public Integer getRequiredVotes() {
        return requiredVotes;
    }

    public void setRequiredVotes(Integer requiredVotes) {
        this.requiredVotes = requiredVotes;
    }

    public Double getRequiredVoteMajority() {
        return requiredVoteMajority;
    }

    public void setRequiredVoteMajority(Double requiredVoteMajority) {
        this.requiredVoteMajority = requiredVoteMajority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrialPeriodConfigPatchDto)) return false;
        TrialPeriodConfigPatchDto that = (TrialPeriodConfigPatchDto) o;
        return Objects.equals(trialPeriodDuration, that.trialPeriodDuration) &&
                Objects.equals(vestingPeriod, that.vestingPeriod) &&
                Objects.equals(requiredVotes, that.requiredVotes) &&
                Objects.equals(requiredVoteMajority, that.requiredVoteMajority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trialPeriodDuration, vestingPeriod, requiredVotes,
                requiredVoteMajority);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TrialPeriodConfigPatchDto.class.getSimpleName() + "[", "]")
                .add("trialPeriodDuration=" + trialPeriodDuration)
                .add("vestingPeriod=" + vestingPeriod)
                .add("requiredVotes=" + requiredVotes)
                .add("requiredVoteMajority=" + requiredVoteMajority)
                .toString();
    }
}
