package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PhoneNumberAttributeConverter implements AttributeConverter<PhoneNumber, String> {
    @Override
    public String convertToDatabaseColumn(PhoneNumber phoneNumber) {
        return phoneNumber.asString();
    }

    @Override
    public PhoneNumber convertToEntityAttribute(String s) {
        return new PhoneNumber(s);
    }
}
