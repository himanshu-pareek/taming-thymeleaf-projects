package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import com.google.common.collect.ImmutableSet;

public interface UserService {
    User createUser(CreateUserParameters parameters);

    ImmutableSet<User> getAllUsers();
}
