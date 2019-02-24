package de.esports.aeq.admins.members.jpa.entity;

import de.esports.aeq.admins.members.domain.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "member")
public class MemberTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column
    private String firstName;

    @Column
    private Gender gender;

    @Column
    private LocalDate dateOfBirth;

    private VerifiableAccountTa teamspeakAccount;

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

    public VerifiableAccountTa getTeamspeakAccount() {
        return teamspeakAccount;
    }

    public void setTeamspeakAccount(VerifiableAccountTa teamspeakAccount) {
        this.teamspeakAccount = teamspeakAccount;
    }
}
