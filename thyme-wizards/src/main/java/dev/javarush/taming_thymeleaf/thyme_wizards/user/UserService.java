package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(CreateUserParameters parameters);

    User createAdministrator(CreateUserParameters parameters);

    Page<User> getAllUsers(Pageable pageable);

  boolean userWithEmailExists(Email email);

  User editUser(UserId userId, EditUserParameters parameters);

  User getUser(UserId userId);

  Optional<User> getUserUsingEmail(String email);

  void deleteUser(UserId userId);

  void deleteAllUsers();

    long countUsers();

  Iterable<UserNameAndId> getAllUsersNameAndId();
}
