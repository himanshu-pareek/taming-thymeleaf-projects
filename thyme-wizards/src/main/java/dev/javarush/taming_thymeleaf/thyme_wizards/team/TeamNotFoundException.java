package dev.javarush.taming_thymeleaf.thyme_wizards.team;

public class TeamNotFoundException extends RuntimeException{
    public TeamNotFoundException(TeamId id) {
        super("Team not found: " + id.asString());
    }
}
