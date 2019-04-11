package de.esports.aeq.admins.account.impl.jpa.entity;

import de.esports.aeq.account.api.jpa.AccountTa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "aeq_acc_group")
public class AccountGroupTa {

    @Id
    @GeneratedValue
    @Column(name = "acc_group_id")
    private Long groupId;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<AccountTa> accounts = new ArrayList<>();

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Collection<AccountTa> getAccounts() {
        return accounts;
    }

    public void setAccounts(Collection<AccountTa> accounts) {
        this.accounts = accounts;
    }
}
