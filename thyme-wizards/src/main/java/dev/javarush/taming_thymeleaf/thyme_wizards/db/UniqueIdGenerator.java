package dev.javarush.taming_thymeleaf.thyme_wizards.db;

public interface UniqueIdGenerator<T> {
    T getNextUniqueId();
}
