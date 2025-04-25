package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import java.util.UUID;
import org.springframework.util.Assert;

public record TeamPlayerId(UUID id) {
  public TeamPlayerId {
    Assert.notNull(id, "id must not be null");
  }

  public TeamPlayerId() {
    this(UUID.randomUUID());
  }

  public String asString() {
    return id.toString();
  }
}
