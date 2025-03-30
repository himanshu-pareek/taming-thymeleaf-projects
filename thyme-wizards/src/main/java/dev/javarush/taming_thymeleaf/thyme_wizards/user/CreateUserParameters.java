package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;

public record CreateUserParameters(Username username, Gender gender, LocalDate birthday,
                                   Email email, PhoneNumber phoneNumber, String password,
                                   MultipartFile avatar) {

  public CreateUserParameters(Username username, Gender gender, LocalDate birthday,
                                   Email email, PhoneNumber phoneNumber, String password) {
    this (username, gender, birthday, email, phoneNumber, password, null);
  }
}
