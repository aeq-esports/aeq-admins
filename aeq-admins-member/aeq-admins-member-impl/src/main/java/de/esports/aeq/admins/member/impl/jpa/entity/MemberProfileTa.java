package de.esports.aeq.admins.member.impl.jpa.entity;

import de.esports.aeq.admins.member.api.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Locale;

@Entity
@Table(name = "aeq_mem_profile")
public class MemberProfileTa implements Serializable {

    @Id
    @Column
    private Long userId;

    @Column
    private String nickname;

    @Column
    private String firstName;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private Locale locale;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
