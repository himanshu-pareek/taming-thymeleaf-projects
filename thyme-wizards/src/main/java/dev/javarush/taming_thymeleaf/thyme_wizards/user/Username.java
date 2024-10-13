package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import jakarta.persistence.Embeddable;
import org.springframework.util.Assert;

import java.util.Objects;

@Embeddable
public class Username {
    private String firstName;
    private String lastName;

    protected Username() {}

    public Username(String firstName, String lastName) {
        Assert.hasText(firstName, "First name can not be null");
        Assert.hasText(lastName, "Last name can not be null");
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Username username = (Username) o;
        return Objects.equals(this.firstName, username.firstName) &&
                Objects.equals(this.lastName, username.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Username{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
