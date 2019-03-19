package de.esports.aeq.admins.platform.api.entity;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "aeq_plt_dta_prop_map")
@DiscriminatorValue("prop_map")
public class PropertyPlatformDataTa extends PlatformDataTa {

    @ElementCollection
    @MapKeyColumn(name = "prop_key")
    @Column(name = "prop_value")
    @CollectionTable(name = "aeq_prop_map",
            joinColumns = @JoinColumn(name = "platform_id"))
    private Map<String, String> properties;

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
