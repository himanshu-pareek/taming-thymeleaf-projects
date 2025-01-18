package dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.security;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.Email;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Gender;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.PhoneNumber;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.User;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserId;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Username;

public class StubUserDetailsService implements UserDetailsService {
    public static final String USERNAME_USER = "john.doe@javarush.dev";
    private static final String USERNAME_ADMIN = "jane.doe@javarush.dev";

    private final Map<String, ApplicationUserDetails> users;

    public StubUserDetailsService(PasswordEncoder passwordEncoder) {
        this.users = new HashMap<>();
        addUser(new ApplicationUserDetails(createUser(passwordEncoder)));
        addUser(new ApplicationUserDetails(createAdmin(passwordEncoder)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(users.get(username))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    private void addUser(ApplicationUserDetails user) {
        users.put(user.getUsername(), user);
    }

    private User createUser(PasswordEncoder passwordEncoder) {
        return User.createUser(
                new UserId(UUID.randomUUID()),
                new Username("John", "Doe"),
                Gender.MALE,
                LocalDate.parse("1990-08-09"),
                new Email(USERNAME_USER),
                new PhoneNumber("+555 666 777"),
                passwordEncoder.encode("secure"));
    }

    private User createAdmin(PasswordEncoder passwordEncoder) {
        return User.createAdministrator(
                new UserId(UUID.randomUUID()),
                new Username("Jane", "Doe"),
                Gender.FEMALE,
                LocalDate.parse("1994-08-09"),
                new Email(USERNAME_ADMIN),
                new PhoneNumber("+555 666 888"),
                passwordEncoder.encode("secure"));
    }

}
