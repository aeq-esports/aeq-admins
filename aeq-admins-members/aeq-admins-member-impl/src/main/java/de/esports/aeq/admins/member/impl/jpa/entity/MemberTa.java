package de.esports.aeq.admins.member.impl.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "aeq_mem")
@NamedEntityGraph(
        name = "MemberTa.connectedAccounts",
        attributeNodes = {
                @NamedAttributeNode("connectedAccounts")
        }
)
public class MemberTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "mem_id")
    private Long id;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberAccountTa account;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberDetailsTa details;

    @OneToMany
    @JoinColumn(name = "acc_id")
    private Collection<ConnectedAccountTa> connectedAccounts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MemberAccountTa getAccount() {
        return account;
    }

    public void setAccount(MemberAccountTa account) {
        this.account = account;
        account.setMember(this);
    }

    public MemberDetailsTa getDetails() {
        return details;
    }

    public void setDetails(MemberDetailsTa details) {
        this.details = details;
        details.setMember(this);
    }

    public Collection<ConnectedAccountTa> getConnectedAccounts() {
        return connectedAccounts;
    }

    public void setConnectedAccounts(
            Collection<ConnectedAccountTa> connectedAccounts) {
        this.connectedAccounts = connectedAccounts;
    }
}
