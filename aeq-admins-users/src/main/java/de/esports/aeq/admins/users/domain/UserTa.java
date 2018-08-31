package de.esports.aeq.admins.users.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "aeq_user")
public class UserTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "aeq_user_id")
    private Long id;

    @OneToOne(mappedBy = "user")
    private UserDetailsTa details;

    @Column
    private String ts3UId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTs3UId() {
        return ts3UId;
    }

    public void setTs3UId(String ts3UId) {
        this.ts3UId = ts3UId;
    }
}
