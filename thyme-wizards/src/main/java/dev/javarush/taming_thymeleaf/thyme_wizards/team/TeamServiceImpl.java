package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.User;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserService;
import java.util.Optional;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamServiceImpl implements TeamService{
  private static final Logger log = org.slf4j.LoggerFactory.getLogger(TeamServiceImpl.class);
  private final TeamRepository teamRepository;
  private final UserService userService;

  public TeamServiceImpl(TeamRepository teamRepository, UserService userService) {
    this.teamRepository = teamRepository;
    this.userService = userService;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<TeamSummary> getTeams(Pageable pageable) {
    return teamRepository.finalAllSummary(pageable);
  }

  @Override
  public Team createTeam(CreateTeamParameters parameters) {
    String teamName = parameters.name();
    User coach = userService.getUser(parameters.coachId());
    log.info("Creating team with name: {}, coach: {} ({})", teamName,
        coach.getUsername().getFullName(), coach.getId());
    Team team = new Team(teamRepository.nextId(), teamName, coach);
    parameters.players().stream()
        .map(player -> new TeamPlayer(teamRepository.nextPlayerId(),
            userService.getUser(player.playerId()), player.position()))
        .forEach(team::addPlayer);
    return teamRepository.save(team);
  }

  @Override
  public Optional<Team> getTeam(TeamId id) {
    return teamRepository.findById(id);
  }

  @Override
  public Team editTeam(TeamId id, EditTeamParameters parameters) {
    Team team = getTeam(id).orElseThrow(() -> new TeamNotFoundException(id));
    if (team.getVersion() != parameters.version()) {
      throw new ObjectOptimisticLockingFailureException(Team.class, team.getId().asString());
    }
    team.setName(parameters.name());
    team.setCoach(userService.getUser(parameters.coachId()));

    team.clearPlayers();
    parameters.players().stream()
        .map(player -> new TeamPlayer(
            teamRepository.nextPlayerId(),
            userService.getUser(player.playerId()),
            player.position()
        ))
        .forEach(team::addPlayer);
    return team;
  }

  @Override
  public void deleteTeam(TeamId id) {
    teamRepository.deleteById(id);
  }

  @Override
  public void deleteAllTeams() {
    teamRepository.deleteAll();
  }

  @Override
  public Optional<Team> getTeamWithPlayers(TeamId id) {
    return teamRepository.findTeamWithPlayers(id);
  }
}
