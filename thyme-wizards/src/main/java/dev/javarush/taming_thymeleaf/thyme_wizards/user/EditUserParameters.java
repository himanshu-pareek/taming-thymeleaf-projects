package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import java.io.IOException;
import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;

public record EditUserParameters(
    Username username, Gender gender, LocalDate birthday,
    Email email, PhoneNumber phoneNumber, MultipartFile avatar, long version
) {

  public EditUserParameters(Username username, Gender gender, LocalDate birthday,
    Email email, PhoneNumber phoneNumber, long version) {
    this(username, gender, birthday, email, phoneNumber, null, version);
  }
  public void update(User user) {
    user.setUsername(username);
    user.setEmail(email);
    user.setBirthday(birthday);
    user.setPhoneNumber(phoneNumber);
    user.setGender(gender);

    if (this.avatar != null && !this.avatar.isEmpty()) {
      try {
        user.setAvatar(this.avatar.getBytes());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
