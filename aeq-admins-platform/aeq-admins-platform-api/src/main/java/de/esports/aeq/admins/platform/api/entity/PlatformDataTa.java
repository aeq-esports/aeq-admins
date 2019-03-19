package de.esports.aeq.admins.platform.api.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "aeq_plt_data")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "data_type")
public class PlatformDataTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "plt_data_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plt_id")
    private PlatformTa platform;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlatformTa getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformTa platform) {
        this.platform = platform;
    }
}
