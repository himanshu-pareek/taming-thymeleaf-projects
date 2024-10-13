package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import org.springframework.util.Assert;

import java.util.Objects;

public class Email {
    private String email;

    protected Email() {}

    public Email(String email) {
        Assert.hasText(email, "Email must not be blank");
        Assert.isTrue(email.contains("@"), "Email must contain @ symbol");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String asString() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email otherEmail = (Email) o;
        return Objects.equals(email, otherEmail.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "Email{" +
                "email='" + email + '\'' +
                '}';
    }
}
