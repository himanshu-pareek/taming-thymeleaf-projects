package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EmailAttributeConverter implements AttributeConverter<Email, String> {
    @Override
    public String convertToDatabaseColumn(Email email) {
        return email.asString();
    }

    @Override
    public Email convertToEntityAttribute(String s) {
        return new Email(s);
    }
}
