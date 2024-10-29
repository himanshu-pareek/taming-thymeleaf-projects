package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.User;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Optional;

public class HasOwnEmailValidator implements ConstraintValidator<HasOwnEmail, EditUserFormData> {

  private final UserService userService;

  public HasOwnEmailValidator(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void initialize(HasOwnEmail constraintAnnotation) {
    // Intentionally kept empty
  }

  @Override
  public boolean isValid(
      EditUserFormData formData,
      ConstraintValidatorContext context
  ) {
    Optional<User> user = this.userService.getUserUsingEmail(formData.getEmail());
    if (user.isEmpty()) {
      return true;
    }
    if (formData.getId().equals(user.get().getId().asString())) {
      return true;
    }
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("{DoesNotOwnEmail}")
        .addPropertyNode("email")
        .addConstraintViolation();
    return false;
  }
}
