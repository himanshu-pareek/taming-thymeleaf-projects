package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import org.springframework.util.Assert;

import java.util.UUID;

public record UserId (UUID id) {
    public UserId {
        Assert.notNull(id, "id must not be null");
    }

    public UserId() {
        this(UUID.randomUUID());
    }

    public String asString() {
        return id.toString();
    }
}
