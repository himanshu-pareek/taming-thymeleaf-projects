package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserId;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUserIdConverter implements Converter<String, UserId> {
  @Override
  public UserId convert(String id) {
    return new UserId(UUID.fromString(id));
  }
}
