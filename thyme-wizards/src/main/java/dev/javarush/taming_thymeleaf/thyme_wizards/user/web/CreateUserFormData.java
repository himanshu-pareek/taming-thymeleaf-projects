package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.validation.ValidationGroupOne;
import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.validation.ValidationGroupTwo;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.CreateUserParameters;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Email;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.PhoneNumber;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Username;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@NonExistingUser(groups = ValidationGroupTwo.class)
@PasswordMatch(groups = ValidationGroupTwo.class)
public class CreateUserFormData extends UserFormData {
  @NotBlank
  @Size(min = 4, max = 50, groups = ValidationGroupOne.class)
  private String password;
  private String passwordRepeated;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPasswordRepeated() {
    return passwordRepeated;
  }

  public void setPasswordRepeated(String passwordRepeated) {
    this.passwordRepeated = passwordRepeated;
  }

  public CreateUserParameters toCreateUserParameters() {
    return new CreateUserParameters(
        new Username(getFirstName(), getLastName()),
        getGender(),
        getBirthday(),
        new Email(getEmail()),
        new PhoneNumber(getPhoneNumber()),
        password);
  }
}
