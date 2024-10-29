package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.validation.ValidationGroupOne;
import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.validation.ValidationGroupThree;
import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({
    Default.class,
    ValidationGroupOne.class,
    ValidationGroupThree.class
})
public interface EditUserValidationGroupSequence {
}
