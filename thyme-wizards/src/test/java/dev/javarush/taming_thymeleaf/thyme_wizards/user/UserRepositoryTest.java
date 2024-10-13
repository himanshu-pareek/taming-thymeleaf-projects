package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import dev.javarush.taming_thymeleaf.thyme_wizards.db.InMemoryUniqueIdGenerator;
import dev.javarush.taming_thymeleaf.thyme_wizards.db.UniqueIdGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("data-jpa-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    UserRepositoryTest(UserRepository userRepository, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void validatePreconditions() {
        assertEquals(0, userRepository.count());
    }

    @Test
    void testSaveUser() {
        UserId id = userRepository.nextId();
        System.out.println("User id (generated) --> " + id);
        userRepository.save(new User(
                id,
                new Username("Himanshu", "Pareek"),
                Gender.MALE,
                LocalDate.of(1997, 1, 1),
                new Email("him@fakeemail.com"),
                new PhoneNumber("9876543210")
        ));

        entityManager.flush();

        assertEquals(id.id(), jdbcTemplate.queryForObject("SELECT id FROM users", UUID.class));
        assertEquals("Himanshu", jdbcTemplate.queryForObject("SELECT first_name FROM users", String.class));
        assertEquals("Pareek", jdbcTemplate.queryForObject("SELECT last_name FROM users", String.class));
        assertEquals(Gender.MALE, jdbcTemplate.queryForObject("SELECT gender FROM users", Gender.class));
        assertEquals(LocalDate.of(1997, 1, 1), jdbcTemplate.queryForObject("SELECT birthday FROM users", LocalDate.class));
        assertEquals("him@fakeemail.com", jdbcTemplate.queryForObject("SELECT email FROM users", String.class));
        assertEquals("9876543210", jdbcTemplate.queryForObject("SELECT phone_number FROM users", String.class));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UniqueIdGenerator<UUID> uniqueIdGenerator() {
            return new InMemoryUniqueIdGenerator();
        }
    }
}