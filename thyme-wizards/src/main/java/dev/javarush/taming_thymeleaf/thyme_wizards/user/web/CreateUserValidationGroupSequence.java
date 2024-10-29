package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.validation.ValidationGroupOne;
import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.validation.ValidationGroupTwo;
import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({
    Default.class,
    ValidationGroupOne.class,
    ValidationGroupTwo.class
})
public interface CreateUserValidationGroupSequence {}
