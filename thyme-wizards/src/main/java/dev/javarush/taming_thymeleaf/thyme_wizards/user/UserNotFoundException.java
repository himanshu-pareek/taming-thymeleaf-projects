package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

  public UserNotFoundException(UserId userId) {
    super(String.format("User with id %s not found", userId.id()));
  }

}
