package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import java.time.LocalDate;
import java.util.UUID;

public class Users {
    public static User createUser() {
        return createUser(new Username("Jon", "Doe"));
    }

    public static User createUser(Username username) {
        String emailAddress = String.format(
                "%s.%s@javarush.dev",
                username.getFirstName().toLowerCase(),
                username.getLastName().toLowerCase());
        return User.createUser(
                new UserId(UUID.randomUUID()),
                username,
                Gender.MALE,
                LocalDate.parse("1997-01-01"),
                new Email(emailAddress),
                new PhoneNumber("+555 666 777"),
                "fake-encoded-password");
    }
}
