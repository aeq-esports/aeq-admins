package de.esports.aeq.admins.members.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AccountIdTa implements Serializable {

    @Column(name = "account_id")
    private String value;

    @Column(name = "account_type")
    private String type;

    private PlatformTa platform;

    public AccountIdTa() {

    }

    public AccountIdTa(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PlatformTa getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformTa platform) {
        this.platform = platform;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountIdTa)) return false;
        AccountIdTa that = (AccountIdTa) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}
