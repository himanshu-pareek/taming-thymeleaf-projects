package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.validation.ValidationGroupOne;
import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.validation.ValidationGroupTwo;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.CreateUserParameters;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Gender;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.PhoneNumber;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Username;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@NonExistingUser(groups = ValidationGroupTwo.class)
public class CreateUserFormData {
  @NotBlank
  @Size(min = 3, max = 50, groups = ValidationGroupOne.class)
  private String firstName;
  @NotBlank
  @Size(min = 3, max = 50, groups = ValidationGroupOne.class)
  private String lastName;
  @NotNull
  private Gender gender;
  @NotBlank
  @Email(groups = ValidationGroupOne.class)
  private String email;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthday;
  @NotBlank
  @Pattern(regexp = "[0-9.\\-() x/+]+", groups = ValidationGroupOne.class)
  private String phoneNumber;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public CreateUserParameters toCreateUserParameters() {
    return new CreateUserParameters(
        new Username(firstName, lastName),
        gender,
        birthday,
        new dev.javarush.taming_thymeleaf.thyme_wizards.user.Email(email),
        new PhoneNumber(phoneNumber),
        firstName.toLowerCase()
    );
  }
}
