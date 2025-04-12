package dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.test;

import dev.javarush.taming_thymeleaf.thyme_wizards.team.TeamService;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.CreateUserParameters;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Email;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Gender;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.PhoneNumber;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserNameAndId;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserService;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Username;
import java.time.LocalDate;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/integration-test")
@Profile("integration-test")
public class IntegrationTestController {
  private final UserService userService;
  private final TeamService teamService;

  public IntegrationTestController(UserService userService, TeamService teamService) {
    this.userService = userService;
    this.teamService = teamService;
  }

  @PostMapping("/reset-db")
  public void reset() {
    teamService.deleteAllTeams();
    userService.deleteAllUsers();

    addUser();
    addAdministrator();
  }

  @PostMapping("/add-test-team")
  public void addTestTeam() {
    UserNameAndId userNameAndId = userService.getAllUsersNameAndId().iterator().next();
    teamService.createTeam("Test Team", userNameAndId.id());
  }

  private void addAdministrator() {
    Username userName = new Username("Admin", "Strator");
      CreateUserParameters parameters = new CreateUserParameters(
          userName,
          Gender.MALE,
          LocalDate.parse("2000-01-01"),
          new Email("admin.strator@javarush.dev"),
          new PhoneNumber("+91 9876543210"),
          userName.getFirstName().toLowerCase()
      );
    userService.createAdministrator(parameters);
  }

  private void addUser() {
Username userName = new Username("Jon", "Doe");
      CreateUserParameters parameters = new CreateUserParameters(
          userName,
          Gender.MALE,
          LocalDate.parse("2000-01-01"),
          new Email("jon.doe@javarush.dev"),
          new PhoneNumber("+91 9876543210"),
          userName.getFirstName().toLowerCase()
      );
    userService.createUser(parameters);
  }
}
