package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import static org.assertj.core.api.Assertions.assertThat;
import dev.javarush.taming_thymeleaf.thyme_wizards.db.InMemoryUniqueIdGenerator;
import dev.javarush.taming_thymeleaf.thyme_wizards.db.UniqueIdGenerator;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.User;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserRepository;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Username;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("data-jpa-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeamRepositoryTest {

  private final TeamRepository teamRepository;
  private final UserRepository userRepository;
  private final JdbcTemplate jdbcTemplate;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  public TeamRepositoryTest(TeamRepository teamRepository, UserRepository userRepository, JdbcTemplate jdbcTemplate) {
    this.teamRepository = teamRepository;
    this.userRepository = userRepository;
    this.jdbcTemplate = jdbcTemplate;
  }

  @BeforeEach
  void validatePreConditions() {
    Assertions.assertEquals(0L, teamRepository.count());
  }

  @Test
  void testSaveTeamWithPlayers() {
    User coach = userRepository.save(Users.createUser(new Username("Coach", "1")));
    // Create player 1, 2 and 3
    User player1 = userRepository.save(Users.createUser(new Username("Player1", "1")));
    User player2 = userRepository.save(Users.createUser(new Username("Player2", "1")));
    User player3 = userRepository.save(Users.createUser(new Username("Player3", "1")));
    // Create team
    TeamId teamId = teamRepository.nextId();
    Team team = new Team(teamId, "Team1", coach);
    team.addPlayer(new TeamPlayer(teamRepository.nextPlayerId(), player1, PlayerPosition.POINT_GUARD));
    team.addPlayer(new TeamPlayer(teamRepository.nextPlayerId(), player2, PlayerPosition.SHOOTING_GUARD));
    team.addPlayer(new TeamPlayer(teamRepository.nextPlayerId(), player3, PlayerPosition.SMALL_FORWARD));

    teamRepository.save(team);

    entityManager.flush();
    entityManager.clear();

    Team teamInDb = teamRepository.findById(teamId).orElseThrow();
    assertThat(teamInDb).isNotNull();
    assertThat(teamInDb.getId()).isEqualTo(teamId);
    assertThat(teamInDb.getName()).isEqualTo("Team1");
    assertThat(teamInDb.getCoach().getId()).isEqualTo(coach.getId());
    assertThat(teamInDb.getPlayers()).hasSize(3);
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    public UniqueIdGenerator<UUID> uniqueIdGenerator() {
        return new InMemoryUniqueIdGenerator();
    }
  }
}
