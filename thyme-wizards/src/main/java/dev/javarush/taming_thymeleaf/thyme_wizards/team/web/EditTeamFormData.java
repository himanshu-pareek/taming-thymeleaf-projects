package dev.javarush.taming_thymeleaf.thyme_wizards.team.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.team.Team;

public class EditTeamFormData extends CreateTeamFormData{
  private String id;
  private long version;

  public static EditTeamFormData fromTeam(Team team) {
    EditTeamFormData formData = new EditTeamFormData();
    formData.setId(team.getId().asString());
    formData.setName(team.getName());
    formData.setCoachId(team.getCoach().getId());
    formData.setVersion(team.getVersion());
    return formData;
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
