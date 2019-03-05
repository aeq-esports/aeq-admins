package de.esports.aeq.admins.members.domain;

import java.io.Serializable;
import java.net.URL;
import java.time.Instant;
import java.util.Collection;

/**
 * Represents a complaint, which basically consists of a reporter, an accuser and one or multiple
 * accused persons.
 * <p>
 * Please note that none of the accounts have to be associated to any registered member. This allows
 * external entities to submit complaints without the need of a registered account. However, if the
 * account can be referenced to an existing member, the related account id should be replaced with
 * the corresponding system id for that member.
 *
 * @implSpec
 */
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
     * Obtains the reporters account id.
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

    /**
     * Obtains the accusers account id.
     *
     * @return the account id, not <code>null</code>
     */
    public AccountId getAccuserAccountId() {
        return accuserAccountId;
    }

    public void setAccuserAccountId(AccountId accuserAccountId) {
        this.accuserAccountId = accuserAccountId;
    }

    /**
     * Obtains all accused account ids.
     *
     * @return a collection of account ids, never <code>null</code>
     */
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
