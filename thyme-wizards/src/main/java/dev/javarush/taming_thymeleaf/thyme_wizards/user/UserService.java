package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(CreateUserParameters parameters);

    Page<User> getAllUsers(Pageable pageable);

  boolean userWithEmailExists(Email email);
}
