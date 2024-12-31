package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, CreateUserFormData> {

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        // Intentionally kept empty
    }

    @Override
    public boolean isValid(CreateUserFormData value, ConstraintValidatorContext context) {
        if (!value.getPassword().equals(value.getPasswordRepeated())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{PasswordsNotMatching}")
                    .addPropertyNode("passwordRepeated")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
