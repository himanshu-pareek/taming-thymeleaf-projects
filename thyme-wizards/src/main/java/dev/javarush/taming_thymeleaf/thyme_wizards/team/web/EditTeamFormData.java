package dev.javarush.taming_thymeleaf.thyme_wizards.team.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.team.CreateTeamParameters;
import dev.javarush.taming_thymeleaf.thyme_wizards.team.EditTeamParameters;
import dev.javarush.taming_thymeleaf.thyme_wizards.team.Team;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EditTeamFormData extends CreateTeamFormData{
  private String id;
  private long version;

  public static EditTeamFormData fromTeam(Team team) {
    EditTeamFormData formData = new EditTeamFormData();
    formData.setId(team.getId().asString());
    formData.setName(team.getName());
    formData.setCoachId(team.getCoach().getId());
    formData.setVersion(team.getVersion());
    formData.setPlayers(
        team.getPlayers().stream()
            .map(TeamPlayerFormData::fromTeamPlayer)
            .toArray(TeamPlayerFormData[]::new)
    );
    return formData;
  }

  @Override
  public CreateTeamParameters toParameters() {
    throw new UnsupportedOperationException("Use toEditParameters() instead");
  }

  public EditTeamParameters toEditParameters() {
    return new EditTeamParameters(
        getName(),
        getCoachId(),
        Arrays.stream(getPlayers()).map(TeamPlayerFormData::toParameters).collect(Collectors.toSet()),
        version
    );
  }

  public String getId() {
      return id;
  }
  public void setId(String id) {
      this.id = id;
  }
  public long getVersion() {
      return version;
  }
  public void setVersion(long version) {
      this.version = version;
  }
}
