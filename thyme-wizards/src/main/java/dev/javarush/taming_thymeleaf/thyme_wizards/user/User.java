package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import jakarta.persistence.*;

@Entity(name = "User")
@Table(name = "users")
public class User {
    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id"))
    private UserId id;

    protected User() {}

    public User(UserId id) {
        this.id = id;
    }
}
