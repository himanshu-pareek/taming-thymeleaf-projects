package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import java.util.UUID;
import org.springframework.util.Assert;

public record TeamId(UUID id) {
    public TeamId {
      Assert.notNull(id, "id must not be null");
    }

    public TeamId() {
        this(UUID.randomUUID());
    }

    public String asString() {
        return id.toString();
    }
}
