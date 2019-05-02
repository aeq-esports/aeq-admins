package de.esports.aeq.admins.account.impl.jpa.entity;

import de.esports.aeq.admins.account.api.jpa.entity.AccountIdTa;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "aeq_mem_conn_acc")
public class ConnectedAccountTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "mem_conn_acc_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "acc_id")
    private AccountIdTa accountId;

    @Column
    private Instant verifiedAt;

    @Column
    private boolean isBanned;

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

    public Instant getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Instant verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
