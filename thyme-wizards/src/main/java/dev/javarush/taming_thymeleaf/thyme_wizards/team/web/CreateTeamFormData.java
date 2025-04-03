package dev.javarush.taming_thymeleaf.thyme_wizards.team.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateTeamFormData {
  @NotBlank
  @Size(min = 3, max = 100)
  private String name;
  @NotNull
  private UserId coachId;

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
}
