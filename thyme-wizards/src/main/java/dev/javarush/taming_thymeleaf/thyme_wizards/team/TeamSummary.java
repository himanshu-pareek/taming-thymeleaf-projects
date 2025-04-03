package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserId;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Username;

public record TeamSummary(
    TeamId id,
    String name,
    UserId coachId,
    Username coachName
) {
}
