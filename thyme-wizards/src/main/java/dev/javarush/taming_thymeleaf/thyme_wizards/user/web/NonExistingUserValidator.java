package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.Email;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.thymeleaf.util.StringUtils;

public class NonExistingUserValidator
    implements ConstraintValidator<NonExistingUser, CreateUserFormData> {

  private final UserService userService;

  public NonExistingUserValidator(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void initialize(NonExistingUser constraintAnnotation) {
    // Intentionally kept empty
  }

  @Override
  public boolean isValid(CreateUserFormData formData, ConstraintValidatorContext context) {
    if (
        !StringUtils.isEmpty(formData.getEmail())
            && userService.userWithEmailExists(new Email(formData.getEmail()))) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("{UserAlreadyExisting}")
          .addPropertyNode("email")
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}
