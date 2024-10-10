package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import dev.javarush.taming_thymeleaf.thyme_wizards.db.UniqueIdGenerator;

import java.util.UUID;

public class UserRepositoryImpl implements UserRepositoryCustom {
    private final UniqueIdGenerator<UUID> uniqueIdGenerator;

    public UserRepositoryImpl(UniqueIdGenerator<UUID> uniqueIdGenerator) {
        this.uniqueIdGenerator = uniqueIdGenerator;
    }

    @Override
    public UserId nextId() {
        return new UserId(uniqueIdGenerator.getNextUniqueId());
    }
}
