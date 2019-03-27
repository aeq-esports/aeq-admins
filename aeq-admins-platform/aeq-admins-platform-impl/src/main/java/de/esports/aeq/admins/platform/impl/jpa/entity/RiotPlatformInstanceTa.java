package de.esports.aeq.admins.platform.impl.jpa.entity;

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

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}
