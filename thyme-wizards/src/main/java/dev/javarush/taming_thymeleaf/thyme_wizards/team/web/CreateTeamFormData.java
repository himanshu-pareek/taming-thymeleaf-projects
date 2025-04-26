package dev.javarush.taming_thymeleaf.thyme_wizards.team.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.team.CreateTeamParameters;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CreateTeamFormData {
  @NotBlank
  @Size(min = 3, max = 100)
  private String name;
  @NotNull
  private UserId coachId;

  @NotNull
  @Size(min = 1)
  @Valid
  private TeamPlayerFormData[] players;

  public CreateTeamFormData() {
    this.players = new TeamPlayerFormData[]{new TeamPlayerFormData()};
  }

  public CreateTeamParameters toParameters() {
    return new CreateTeamParameters(
        name,
        coachId,
        Arrays.stream(players).map(TeamPlayerFormData::toParameters).collect(Collectors.toSet())
    );
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserId getCoachId() {
    return coachId;
  }

  public void setCoachId(UserId coachId) {
    this.coachId = coachId;
  }

  public TeamPlayerFormData[] getPlayers() {
    return players;
  }

  public void setPlayers(TeamPlayerFormData[] players) {
    this.players = players;
  }
}
