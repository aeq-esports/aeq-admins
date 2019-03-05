package de.esports.aeq.admins.members.domain;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Member implements Serializable {

    private Long id;
    private String firstName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private VerifiableAccount teamspeakAccount;

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

    /**
     * Obtains the current age of this member using the system clock in the default time-zone.
     *
     * @return a {@link Period} representing the current age
     */
    public Period getAge() {
        return Period.between(dateOfBirth, LocalDate.now());
    }

    public VerifiableAccount getTeamspeakAccount() {
        return teamspeakAccount;
    }

    public void setTeamspeakAccount(@Nullable VerifiableAccount teamspeakAccount) {
        this.teamspeakAccount = teamspeakAccount;
    }
}
