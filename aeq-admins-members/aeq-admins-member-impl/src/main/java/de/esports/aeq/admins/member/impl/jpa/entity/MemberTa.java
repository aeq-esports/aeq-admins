package de.esports.aeq.admins.members.jpa.entity;

import de.esports.aeq.admins.account.api.Account;
import de.esports.aeq.admins.member.api.MemberDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "mer_member")
public class MemberTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private MemberDetails data;

    @Column
    private Instant createdAt;

    @Column
    private Instant lastSeenAt;

    @Column
    private Boolean isBanned;

    private Collection<Account> accounts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
