package dev.javarush.taming_thymeleaf.thyme_wizards.team.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.team.TeamId;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTeamIdConverter implements Converter<String, TeamId> {
  @Override
  public TeamId convert(String id) {
    return new TeamId(UUID.fromString(id));
  }
}
