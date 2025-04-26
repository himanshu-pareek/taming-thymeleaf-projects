package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.User;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamService {
  Page<TeamSummary> getTeams(Pageable pageable);
  Team createTeam(CreateTeamParameters parameters);
  Optional<Team> getTeam(TeamId id);
  Team editTeam(TeamId id, EditTeamParameters parameters);
  void deleteTeam(TeamId id);
  void deleteAllTeams();

  Optional<Team> getTeamWithPlayers(TeamId id);
}
