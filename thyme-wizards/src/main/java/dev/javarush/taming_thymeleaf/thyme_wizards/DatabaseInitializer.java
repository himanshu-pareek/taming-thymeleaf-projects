package dev.javarush.taming_thymeleaf.thyme_wizards;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
@Profile("init-db")
public class DatabaseInitializer implements CommandLineRunner {
  private final Faker faker = new Faker();
  private final UserService userService;

  public DatabaseInitializer(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i < 20; i++) {
      CreateUserParameters parameters = newRandomUserParameters();
      userService.createUser(parameters);
    }

    Username userName = randomUserName();
      CreateUserParameters parameters = new CreateUserParameters(
          userName,
          Gender.MALE,
          LocalDate.parse("2000-01-01"),
          generateEmailForUserName(userName),
          randomPhoneNumber(),
          userName.getFirstName().toLowerCase()
      );
    userService.createAdministrator(parameters);
  }

  private CreateUserParameters newRandomUserParameters() {
    Username username = randomUserName();
    Gender gender = faker.bool().bool() ? Gender.MALE : Gender.FEMALE;
    LocalDate birthday =
        LocalDate.ofInstant(faker.date().birthday(10, 40).toInstant(), ZoneId.systemDefault());
    Email email = generateEmailForUserName(username);
    PhoneNumber phoneNumber = randomPhoneNumber();
    return new CreateUserParameters(
        username,
        gender,
        birthday,
        email,
        phoneNumber,
        username.getFirstName().toLowerCase()
    );
  }

  private PhoneNumber randomPhoneNumber() {
    return new PhoneNumber(faker.phoneNumber().phoneNumber());
  }

  private Email generateEmailForUserName(Username username) {
    return new Email(faker.internet().emailAddress(generateEmailLocalPart(username)));
  }

  private Username randomUserName() {
    Name name = faker.name();
    return new Username(name.firstName(), name.lastName());
  }

  private String generateEmailLocalPart(Username username) {
    return String.format(
        "%s.%s",
        StringUtils.remove(username.getFirstName().toLowerCase(), "'"),
        StringUtils.remove(username.getLastName().toLowerCase(), "'")
    );
  }
}
