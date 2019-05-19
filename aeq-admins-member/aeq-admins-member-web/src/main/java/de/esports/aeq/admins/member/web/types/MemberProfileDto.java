package de.esports.aeq.admins.member.web.types;

import de.esports.aeq.admins.member.api.Gender;

import java.time.LocalDate;
import java.util.Locale;

public class MemberProfileDto {

    private Long userId;
    private String firstName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private int age;
    private Locale locale;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        this.age = LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    public int getAge() {
        return age;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
