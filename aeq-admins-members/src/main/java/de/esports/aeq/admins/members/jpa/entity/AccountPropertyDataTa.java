package de.esports.aeq.admins.members.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name = "account_property_data")
public class AccountPropertyDataTa implements Serializable {

    @EmbeddedId
    private AccountIdTa accountId;

    @ElementCollection
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    @CollectionTable(name = "property_data_map", joinColumns = @JoinColumn(name = "account_id"))
    private Map<String, String> properties;

    public AccountIdTa getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountIdTa accountId) {
        this.accountId = accountId;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
