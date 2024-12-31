package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordMatch {
    String message() default "{PasswordsNotMatching}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
