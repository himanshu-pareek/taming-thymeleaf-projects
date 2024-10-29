package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NonExistingUserValidator.class)
public @interface NonExistingUser {

  String message() default "{UserAlreadyExisting}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}