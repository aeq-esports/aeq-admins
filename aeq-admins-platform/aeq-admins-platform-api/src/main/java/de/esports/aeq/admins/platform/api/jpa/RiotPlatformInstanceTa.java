package de.esports.aeq.admins.platform.api.jpa;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "aeq_plt_inst_riot")
@DiscriminatorValue("plt_inst_riot")
public class RiotPlatformInstanceTa extends PlatformInstanceTa {

    @Column
    private String regionId;

    @Column
    private String regionName;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
