package de.esports.aeq.admins.account.api.jpa.entity;

import de.esports.aeq.admins.platform.api.jpa.PlatformTa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "aeq_acc_id")
public class AccountIdTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "acc_id")
    private Long id;

    @Column
    private String value;

    @Column
    private String type;

    @Column
    private PlatformTa platform;

    public AccountIdTa() {

    }

    public AccountIdTa(String value, String type, PlatformTa platform) {
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
