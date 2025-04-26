package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserId;
import java.util.Set;

public record CreateTeamParameters(
    String name,
    UserId coachId,
    Set<TeamPlayerParameters> players
) {
}
