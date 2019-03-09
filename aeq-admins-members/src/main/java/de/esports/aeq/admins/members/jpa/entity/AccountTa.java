package de.esports.aeq.admins.members.jpa.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
public class AccountTa implements Serializable {

    @EmbeddedId
    private AccountIdTa accountId;

    @Column
    private Instant createdAt;

    @Column
    private Instant lastSeen;

    @Column(name = "data_type")
    private Class<?> dataType;

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

    public Class<?> getDataType() {
        return dataType;
    }

    public void setDataType(Class<?> dataType) {
        this.dataType = dataType;
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
