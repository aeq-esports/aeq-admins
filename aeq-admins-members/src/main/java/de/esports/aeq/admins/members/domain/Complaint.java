package de.esports.aeq.admins.members.domain;

import java.io.Serializable;
import java.net.URL;
import java.time.Instant;
import java.util.Collection;

public class Complaint implements Serializable {

    private Long id;
    private AccountId reporter;
    private AccountId accuserAccountId;
    private Collection<AccountId> accusedAccountIds;
    private Instant createdAt;
    private String description;
    private Collection<URL> attachments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtains the reporter of this complaint.
     * <p>
     * If the reporter is the same person as the accuser, the returned account id will be the same
     * as the accuser id.
     *
     * @return the reporters account id
     */
    public AccountId getReporter() {
        return reporter;
    }

    public void setReporter(AccountId reporter) {
        this.reporter = reporter;
    }

    public AccountId getAccuserAccountId() {
        return accuserAccountId;
    }

    public void setAccuserAccountId(AccountId accuserAccountId) {
        this.accuserAccountId = accuserAccountId;
    }

    public Collection<AccountId> getAccusedAccountIds() {
        return accusedAccountIds;
    }

    public void setAccusedAccountIds(
            Collection<AccountId> accusedAccountIds) {
        this.accusedAccountIds = accusedAccountIds;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<URL> getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection<URL> attachments) {
        this.attachments = attachments;
    }
}
