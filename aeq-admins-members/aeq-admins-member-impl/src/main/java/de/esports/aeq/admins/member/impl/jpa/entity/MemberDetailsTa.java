package de.esports.aeq.admins.members.jpa.entity;

import de.esports.aeq.admins.member.api.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Locale;

@Entity
@Table(name = "aeq_mem_det")
public class MemberDetailsTa implements Serializable {

    @MapsId
    @OneToOne
    private MemberTa member;

    @Column
    private String firstName;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private Locale locale;

    public MemberTa getMember() {
        return member;
    }

    public void setMember(MemberTa member) {
        this.member = member;
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
}
