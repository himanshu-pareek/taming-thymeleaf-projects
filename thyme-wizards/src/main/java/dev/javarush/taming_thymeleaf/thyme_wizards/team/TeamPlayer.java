package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.User;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity(name = "TeamPlayer")
@Table(name = "team-players")
public class TeamPlayer {
  @EmbeddedId
  @AttributeOverride(name = "id", column = @Column(name = "id"))
  private TeamPlayerId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private Team team;

  @OneToOne
  @NotNull
  private User player;

  @Enumerated(EnumType.STRING)
  @NotNull
  private PlayerPosition position;

  protected TeamPlayer() {}

  private TeamPlayer(TeamPlayerId id, User player, PlayerPosition position) {
    this.id = id;
    this.player = player;
    this.position = position;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public Team getTeam() {
    return team;
  }

  public User getPlayer() {
    return player;
  }

  public PlayerPosition getPosition() {
    return position;
  }

  public TeamPlayerId getId() {
    return id;
  }
}
