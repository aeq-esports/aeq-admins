package de.esports.aeq.admins.platform.api.data;

import de.esports.aeq.admins.platform.api.PlatformInstance;

import java.util.Objects;
import java.util.StringJoiner;

public class RiotPlatformInstance implements PlatformInstance {

    private Long id;
    private String regionId;
    private String regionName;

    public RiotPlatformInstance() {

    }

    public RiotPlatformInstance(String regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;
    }

    @Override
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RiotPlatformInstance)) return false;
        RiotPlatformInstance that = (RiotPlatformInstance) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(regionName, that.regionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regionId, regionName);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RiotPlatformInstance.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("regionId='" + regionId + "'")
                .add("regionName='" + regionName + "'")
                .toString();
    }
}
