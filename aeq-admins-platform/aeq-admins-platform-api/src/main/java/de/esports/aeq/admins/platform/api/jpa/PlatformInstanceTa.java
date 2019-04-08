package de.esports.aeq.admins.platform.api.jpa;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "aeq_plt_data")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "data_type")
public class PlatformInstanceTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "plt_data_id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}