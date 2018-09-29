package de.esports.aeq.admins.application.domain;

import de.esports.aeq.admins.security.domain.UserDetailsTa;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "aeq_application")
public class ApplicationTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "aeq_application_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "aeq_user_id")
    private UserDetailsTa userDetails;

    @Lob
    @Column
    private String text;

    @Enumerated
    private ApplicationStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserDetailsTa getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetailsTa userDetails) {
        this.userDetails = userDetails;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
