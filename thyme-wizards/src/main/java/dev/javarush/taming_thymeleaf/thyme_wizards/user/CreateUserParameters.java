package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import java.time.LocalDate;

public class CreateUserParameters {
    private final Username username;
    private final Gender gender;
    private final LocalDate birthday;
    private final Email email;
    private final PhoneNumber phoneNumber;

    public CreateUserParameters(Username username, Gender gender, LocalDate birthday, Email email, PhoneNumber phoneNumber) {
        this.username = username;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Username getUsername() {
        return username;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Email getEmail() {
        return email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
}
