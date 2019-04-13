package de.esports.aeq.admins.member.impl.jpa.entity;

import de.esports.aeq.admins.account.api.jpa.entity.AccountIdTa;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "aeq_mem_acc")
public class MemberAccountTa implements Serializable {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id")
    private MemberTa member;

    @Column(name = "acc_id")
    private AccountIdTa accountId;

    @Column
    private Instant createdAt;

    @Column
    private Instant lastSeenAt;

    @Column
    private boolean isBanned;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mem_acc_id")
    private Collection<ConnectedAccountTa> connectedAccounts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MemberTa getMember() {
        return member;
    }

    public void setMember(MemberTa member) {
        this.member = member;
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

    public Instant getLastSeenAt() {
        return lastSeenAt;
    }

    public void setLastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public Collection<ConnectedAccountTa> getConnectedAccounts() {
        return connectedAccounts;
    }

    public void setConnectedAccounts(
            Collection<ConnectedAccountTa> connectedAccounts) {
        this.connectedAccounts = connectedAccounts;
    }
}
