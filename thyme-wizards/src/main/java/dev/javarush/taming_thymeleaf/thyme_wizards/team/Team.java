package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.User;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;

@Entity(name = "Team")
@Table(name = "teams")
public class Team {

  @EmbeddedId
  @AttributeOverride(name = "id", column = @Column(name = "id"))
  private TeamId id;

  @NotBlank
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private User coach;

  @Version
  private long version;

  protected Team() {}

  public Team(TeamId id, String name, User coach) {
    this.id = id;
    this.name = name;
    this.coach = coach;
  }

  public @NotBlank String getName() {
    return name;
  }

  public void setName(@NotBlank String name) {
    this.name = name;
  }

  public User getCoach() {
    return coach;
  }

  public void setCoach(User coach) {
    this.coach = coach;
  }

  public TeamId getId() {
    return id;
  }

  public void setId(TeamId id) {
    this.id = id;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }
}
