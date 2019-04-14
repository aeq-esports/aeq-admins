package de.esports.aeq.admins.member.impl.jpa.entity;

import de.esports.aeq.admins.member.api.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

@Entity
@Table(name = "aeq_mem")
@NamedEntityGraph(
        name = "MemberTa.connectedAccounts",
        attributeNodes = {
                @NamedAttributeNode("connectedAccounts")
        }
)
public class MemberTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "mem_id")
    private Long id;

    @Column
    private String firstName;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private Locale locale;

    @Column
    private Instant createdAt;

    @Column
    private Instant lastSeenAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mem_id")
    private Collection<ConnectedAccountTa> connectedAccounts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getLastSeenAt() {
        return lastSeenAt;
    }

    public void setLastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }
}
