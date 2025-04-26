package dev.javarush.taming_thymeleaf.thyme_wizards.team;

import java.util.Set;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserId;

public record EditTeamParameters(
    String name, UserId coachId, Set<TeamPlayerParameters> players, long version
) {
}
