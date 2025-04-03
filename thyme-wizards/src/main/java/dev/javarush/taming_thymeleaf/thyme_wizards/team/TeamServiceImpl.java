package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.User;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserId;
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
  public Team createTeam(String name, User coach) {
    log.info("Creating team {} with coach {} ({})", name, coach.getUsername().getFullName(), coach.getId());
    return teamRepository.save(new Team(teamRepository.nextId(), name, coach));
  }

  @Override
  public Team createTeam(String name, UserId coachId) {
    User coach = userService.getUser(coachId);
    return createTeam(name, coach);
  }

  @Override
  public Optional<Team> getTeam(TeamId id) {
    return teamRepository.findById(id);
  }

  @Override
  public Team editTeam(TeamId id, String name, long version, UserId coachId) {
    Team team = getTeam(id).orElseThrow(() -> new TeamNotFoundException(id));
    if (team.getVersion() != version) {
      throw new ObjectOptimisticLockingFailureException(Team.class, team.getId().asString());
    }
    team.setName(name);
    team.setCoach(userService.getUser(coachId));
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
}
