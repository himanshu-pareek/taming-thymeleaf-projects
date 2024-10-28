package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import java.time.LocalDate;

public record EditUserParameters(
    Username username, Gender gender, LocalDate birthday,
    Email email, PhoneNumber phoneNumber, long version
) {
  public void update(User user) {
    user.setUsername(username);
    user.setEmail(email);
    user.setBirthday(birthday);
    user.setPhoneNumber(phoneNumber);
    user.setGender(gender);
  }
}
