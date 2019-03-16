package de.esports.aeq.admins.members.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "platform")
public class PlatformTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "platform_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String type;

    @OneToOne(mappedBy = "platform", cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY)
    private PlatformDataTa data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PlatformDataTa getData() {
        return data;
    }

    public void setData(PlatformDataTa data) {
        this.data = data;
    }
}
