package de.esports.aeq.admins.trials.service.dto;

import de.esports.aeq.admins.trials.common.TrialState;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class CreateTrialPeriodVote implements Serializable {

    @NotNull
    private Long trialPeriodId;
    @NotNull
    private Long userId;
    @NotNull
    private TrialState consensus;
    private String comment;

    public Long getTrialPeriodId() {
        return trialPeriodId;
    }

    public void setTrialPeriodId(Long trialPeriodId) {
        this.trialPeriodId = trialPeriodId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
        if (!(o instanceof CreateTrialPeriodVote)) return false;
        CreateTrialPeriodVote that = (CreateTrialPeriodVote) o;
        return Objects.equals(trialPeriodId, that.trialPeriodId) &&
                Objects.equals(userId, that.userId) &&
                consensus == that.consensus &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trialPeriodId, userId, consensus, comment);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CreateTrialPeriodVote.class.getSimpleName() + "[", "]")
                .add("trialPeriodId=" + trialPeriodId)
                .add("userId=" + userId)
                .add("consensus=" + consensus)
                .add("comment='" + comment + "'")
                .toString();
    }
}
