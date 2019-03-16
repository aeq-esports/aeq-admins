package de.esports.aeq.admins.members.jpa.entity;

        import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "data_type")
public class PlatformDataTa {

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private PlatformTa platform;

    public PlatformTa getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformTa platform) {
        this.platform = platform;
    }
}
