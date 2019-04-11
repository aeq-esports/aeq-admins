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

    private Collection<AccountTa> accounts = new ArrayList<>();
}
