package de.esports.aeq.account.api.jpa;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "account_data_property_map")
@DiscriminatorValue("PROPERTY_MAP")
public class AccountPropertyDataTa extends AccountDataTa {

    @ElementCollection
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    @CollectionTable(name = "property_map_data", joinColumns = @JoinColumn(name = "account_id"))
    private Map<String, String> properties;

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
