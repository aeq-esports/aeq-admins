package de.esports.aeq.admins.account.api.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "aeq_acc")
public class AccountTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "acc_id")
    private Long id;

    @Column
    private AccountIdTa accountId;

    @Column
    private Instant createdAt;

    @Column
    private Instant lastSeen;

    @Column
    private Instant verifiedAt;

    @Column
    private Boolean isBanned;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountIdTa getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountIdTa accountId) {
        this.accountId = accountId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Instant getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Instant verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public Boolean isBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountTa)) return false;
        AccountTa accountTa = (AccountTa) o;
        return Objects.equals(accountId, accountTa.accountId) &&
                Objects.equals(createdAt, accountTa.createdAt) &&
                Objects.equals(lastSeen, accountTa.lastSeen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, createdAt, lastSeen);
    }
}
