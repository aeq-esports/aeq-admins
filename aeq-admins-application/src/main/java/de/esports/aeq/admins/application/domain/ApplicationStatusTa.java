package de.esports.aeq.admins.application.domain;

import de.esports.aeq.admins.common.Auditable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "aeq_application_status")
public class ApplicationStatusTa extends Auditable implements Serializable {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "application_id")
    private ApplicationTa application;

    @Enumerated
    private ApplicationStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationTa getApplication() {
        return application;
    }

    public void setApplication(ApplicationTa application) {
        this.application = application;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
