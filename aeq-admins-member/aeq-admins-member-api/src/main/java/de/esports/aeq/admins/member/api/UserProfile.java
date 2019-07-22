package de.esports.aeq.admins.member.api;

import java.time.LocalDate;
import java.util.Locale;

public class UserProfile {

    public static UserProfile empty(Long userId) {
        return new UserProfile(userId);
    }

    private Long userId;

    private String firstName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Locale locale;

    public UserProfile() {

    }

    private UserProfile(Long userId) {
        this.userId = userId;
    }

    public int getAge() {
        if (dateOfBirth == null) {
            throw new IllegalStateException("date of birth is not set");
        }
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

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
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
