package de.esports.aeq.admins.application.domain;

import de.esports.aeq.admins.common.Auditable;
import de.esports.aeq.admins.users.domain.UserDetailsTa;
import de.esports.aeq.admins.users.domain.UserTa;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "aeq_application")
public class ApplicationTa extends Auditable implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "aeq_application_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "aeq_user_id")
    private UserTa user;

    @Lob
    @Column
    private String text;

    @OneToOne(mappedBy = "user")
    private UserDetailsTa details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserTa getUser() {
        return user;
    }

    public void setUser(UserTa user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserDetailsTa getDetails() {
        return details;
    }

    public void setDetails(UserDetailsTa details) {
        this.details = details;
    }
}
