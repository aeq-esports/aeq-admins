package de.esports.aeq.admins.complaint.api.jpa;

import de.esports.aeq.account.api.jpa.AccountIdTa;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URL;
import java.time.Instant;
import java.util.Collection;

@Entity
@Table(name = "complaint")
public class ComplaintTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "complaint_id")
    private Long id;

    @Embedded
    private AccountIdTa reporterAccountId;

    @Embedded
    private AccountIdTa accuserAccountId;

    @ElementCollection
    @CollectionTable(
            name = "complaint_accused_account_ids",
            joinColumns = @JoinColumn(name = "complaint_id")
    )
    private Collection<AccountIdTa> accusedAccountIds;

    @Column(name = "created_at")
    private Instant createdAt;

    @Lob
    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(
            name = "complaint_attachments",
            joinColumns = @JoinColumn(name = "complaint_id")
    )
    private Collection<URL> attachments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountIdTa getReporterAccountId() {
        return reporterAccountId;
    }

    public void setReporterAccountId(AccountIdTa reporterAccountId) {
        this.reporterAccountId = reporterAccountId;
    }

    public AccountIdTa getAccuserAccountId() {
        return accuserAccountId;
    }

    public void setAccuserAccountId(AccountIdTa accuserAccountId) {
        this.accuserAccountId = accuserAccountId;
    }

    public Collection<AccountIdTa> getAccusedAccountIds() {
        return accusedAccountIds;
    }

    public void setAccusedAccountIds(
            Collection<AccountIdTa> accusedAccountIds) {
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
