package dev.javarush.taming_thymeleaf.thyme_wizards.team.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.team.PlayerPosition;
import dev.javarush.taming_thymeleaf.thyme_wizards.team.TeamPlayer;
import dev.javarush.taming_thymeleaf.thyme_wizards.team.TeamPlayerParameters;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserId;
import jakarta.validation.constraints.NotNull;

public class TeamPlayerFormData {
  @NotNull
  private UserId playerId;
  @NotNull
  private PlayerPosition position;

  public UserId getPlayerId() {
    return playerId;
  }

  public PlayerPosition getPosition() {
    return position;
  }

  public void setPlayerId(UserId playerId) {
    this.playerId = playerId;
  }

  public void setPosition(PlayerPosition position) {
    this.position = position;
  }

  public static TeamPlayerFormData fromTeamPlayer(TeamPlayer player) {
    var result = new TeamPlayerFormData();
    result.setPlayerId(player.getPlayer().getId());
    result.setPosition(player.getPosition());
    return result;
  }

  public TeamPlayerParameters toParameters() {
    return new TeamPlayerParameters(
        playerId,
        position
    );
  }
}
