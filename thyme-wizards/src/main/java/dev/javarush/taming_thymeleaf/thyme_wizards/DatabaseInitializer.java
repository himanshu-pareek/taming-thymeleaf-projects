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
    }

    private CreateUserParameters newRandomUserParameters() {
        Name name = faker.name();
        Username username = new Username(name.firstName(), name.lastName());
        Gender gender = faker.bool().bool() ? Gender.MALE : Gender.FEMALE;
        LocalDate birthday = LocalDate.ofInstant(faker.date().birthday(10, 40).toInstant(), ZoneId.systemDefault());
        Email email = new Email(faker.internet().emailAddress(generateEmailLocalPart(username)));
        PhoneNumber phoneNumber = new PhoneNumber(faker.phoneNumber().phoneNumber());
        return new CreateUserParameters(
                username,
                gender,
                birthday,
                email,
                phoneNumber
        );
    }

    private String generateEmailLocalPart(Username username) {
        return String.format(
                "%s.%s",
                StringUtils.remove(username.getFirstName().toLowerCase(), "'"),
                StringUtils.remove(username.getLastName().toLowerCase(), "'")
        );
    }
}
