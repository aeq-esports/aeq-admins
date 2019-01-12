package de.esports.aeq.admins.trials.service.dto;

import de.esports.aeq.admins.trials.common.TrialState;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class UpdateTrialPeriodVote implements Serializable {

    @NotNull
    private Long id;
    @NotNull
    private TrialState consensus;
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof UpdateTrialPeriodVote)) return false;
        UpdateTrialPeriodVote that = (UpdateTrialPeriodVote) o;
        return Objects.equals(id, that.id) &&
                consensus == that.consensus &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, consensus, comment);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UpdateTrialPeriodVote.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("consensus=" + consensus)
                .add("comment='" + comment + "'")
                .toString();
    }
}
