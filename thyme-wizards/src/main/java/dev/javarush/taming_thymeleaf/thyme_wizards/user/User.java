package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity(name = "User")
@Table(name = "users")
public class User {
    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id"))
    private UserId id;

    @NotNull
    private Username username;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    private LocalDate birthday;

    @NotNull
    private Email email;

    @NotNull
    private PhoneNumber phoneNumber;

    protected User() {}

    public User(UserId id, Username username, Gender gender, LocalDate birthday, Email email, PhoneNumber phoneNumber) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UserId getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public Gender getGender() {
        return gender;
    }

    public @NotNull LocalDate getBirthday() {
        return birthday;
    }

    public Email getEmail() {
        return email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username=" + username +
            ", gender=" + gender +
            ", birthday=" + birthday +
            ", email=" + email +
            ", phoneNumber=" + phoneNumber +
            '}';
    }
}
