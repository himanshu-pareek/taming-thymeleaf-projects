package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

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

public class CreateUserFormData {
  @NotBlank
  @Size(min = 3, max = 50)
  private String firstName;
  @NotBlank
  @Size(min = 3, max = 50)
  private String lastName;
  @NotNull
  private Gender gender;
  @NotBlank
  @Email
  @Size(min = 3, max = 100)
  private String email;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthday;
  @NotBlank
  @Pattern(regexp = "[0-9.\\-() x/+]+")
  @Size(min = 8, max = 15)
  private String phoneNumber;

  public @NotBlank String getFirstName() {
    return firstName;
  }

  public void setFirstName(@NotBlank String firstName) {
    this.firstName = firstName;
  }

  public @NotBlank String getLastName() {
    return lastName;
  }

  public void setLastName(@NotBlank String lastName) {
    this.lastName = lastName;
  }

  public @NotNull Gender getGender() {
    return gender;
  }

  public void setGender(
      @NotNull Gender gender) {
    this.gender = gender;
  }

  public @NotBlank String getEmail() {
    return email;
  }

  public void setEmail(@NotBlank String email) {
    this.email = email;
  }

  public @NotNull LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(@NotNull LocalDate birthday) {
    this.birthday = birthday;
  }

  public @NotBlank @Pattern(regexp = "[0-9.\\-() x/+]+") String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(
      @NotBlank @Pattern(regexp = "[0-9.\\-() x/+]+") String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public CreateUserParameters toParameters() {
    return new CreateUserParameters(
        new Username(firstName, lastName),
        gender,
        birthday,
        new dev.javarush.taming_thymeleaf.thyme_wizards.user.Email(email),
        new PhoneNumber(phoneNumber)
    );
  }
}
