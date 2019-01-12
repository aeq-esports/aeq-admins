package de.esports.aeq.admins.trials.web.dto;

import de.esports.aeq.admins.trials.domain.TrialState;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class TrialPeriodVoteCreateDto implements Serializable {

    @NotNull
    private TrialState consensus;
    private String comment;

    public TrialState getConsensus() {
        return consensus;
    }

    public void setConsensus(TrialState consensus) {
        this.consensus = consensus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrialPeriodVoteCreateDto)) return false;
        TrialPeriodVoteCreateDto that = (TrialPeriodVoteCreateDto) o;
        return consensus == that.consensus &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consensus, comment);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TrialPeriodVoteCreateDto.class.getSimpleName() + "[", "]")
                .add("consensus=" + consensus)
                .add("comment='" + comment + "'")
                .toString();
    }
}
