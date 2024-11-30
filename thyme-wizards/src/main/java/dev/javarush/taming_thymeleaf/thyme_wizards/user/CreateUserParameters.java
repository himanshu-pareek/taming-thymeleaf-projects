package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import java.time.LocalDate;

public record CreateUserParameters(Username username, Gender gender, LocalDate birthday,
                                   Email email, PhoneNumber phoneNumber, String password) {
}
