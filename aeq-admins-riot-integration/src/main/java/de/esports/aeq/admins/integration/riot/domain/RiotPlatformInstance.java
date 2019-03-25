package de.esports.aeq.admins.integration.riot.domain;

import de.esports.aeq.admins.platform.api.PlatformInstance;

public final class RiotPlatformInstance implements PlatformInstance {

    private Long id;
    private String regionId;
    private String regionName;

    public RiotPlatformInstance() {
        
    }

    public RiotPlatformInstance(String regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
