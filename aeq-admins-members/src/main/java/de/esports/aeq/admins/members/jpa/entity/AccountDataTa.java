package de.esports.aeq.admins.members.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "data_type")
public class AccountDataTa implements Serializable {

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private AccountTa account;

    public AccountTa getAccount() {
        return account;
    }

    public void setAccount(AccountTa account) {
        this.account = account;
    }
}
