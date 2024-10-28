package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import jakarta.persistence.Version;

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

    @Version
    private long version;

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

    public long getVersion() {
        return version;
    }

    public void setUsername(
        @NotNull Username username) {
        this.username = username;
    }

    public void setBirthday(@NotNull LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setEmail(
        @NotNull Email email) {
        this.email = email;
    }

    public void setGender(
        @NotNull Gender gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(
        @NotNull PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setVersion(long version) {
        this.version = version;
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
            ", version = " + version +
            '}';
    }
}
