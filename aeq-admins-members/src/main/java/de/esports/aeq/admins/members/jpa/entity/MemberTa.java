package de.esports.aeq.admins.members.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "mer_member")
public class MemberTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private AccountTa account;

    private List<AccountTa> accounts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountTa getAccount() {
        return account;
    }

    public void setAccount(AccountTa account) {
        this.account = account;
    }

    public List<AccountTa> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountTa> accounts) {
        this.accounts = accounts;
    }
}
