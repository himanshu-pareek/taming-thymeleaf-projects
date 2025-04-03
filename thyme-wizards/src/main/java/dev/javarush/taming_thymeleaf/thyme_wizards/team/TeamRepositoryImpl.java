package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import dev.javarush.taming_thymeleaf.thyme_wizards.db.UniqueIdGenerator;
import java.util.UUID;

public class TeamRepositoryImpl implements TeamRepositoryCustom {
  private final UniqueIdGenerator<UUID> uniqueIdGenerator;

  public TeamRepositoryImpl(UniqueIdGenerator<UUID> uniqueIdGenerator) {
    this.uniqueIdGenerator = uniqueIdGenerator;
  }

  @Override
  public TeamId nextId() {
    return new TeamId(uniqueIdGenerator.getNextUniqueId());
  }
}
