package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import org.springframework.util.Assert;

import java.util.Objects;

public class PhoneNumber {
    private String phoneNumber;

    protected PhoneNumber() {}

    public PhoneNumber(String phoneNumber) {
        Assert.hasText(phoneNumber, "Phone number can not be blank");
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String asString() {
        return this.phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(phoneNumber);
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
