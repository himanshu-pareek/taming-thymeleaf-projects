package dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.validation;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({
    Default.class,
    ValidationGroupOne.class,
    ValidationGroupTwo.class
})
public interface ValidationGroupSequence {}
