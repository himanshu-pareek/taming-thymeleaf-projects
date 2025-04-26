package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserId;

public record TeamPlayerParameters(
    UserId playerId,
    PlayerPosition position
) {
}
