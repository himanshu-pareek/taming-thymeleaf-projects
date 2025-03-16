package dev.javarush.taming_thymeleaf.thyme_wizards;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserService;
import io.github.wimdeblauwe.testcontainers.cypress.CypressContainer;
import io.github.wimdeblauwe.testcontainers.cypress.CypressTestResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("integration-test")
public class CypressE2ETests {
    @Container
    private static final PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:16-bullseye")
            .withDatabaseName("thyme_db")
            .withUsername("test_dev")
            .withPassword("password");

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @DynamicPropertySource
    static void setup(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @BeforeEach
    void validatePreConditions() {
        assertThat(userService.countUsers()).isZero();
    }

    @Test
    void test() throws IOException, InterruptedException, TimeoutException {
        System.out.println("port = " + port);
        System.out.println("Application started");
        org.testcontainers.Testcontainers.exposeHostPorts(port);
        try (CypressContainer cypressContainer = new CypressContainer().withLocalServerPort(port)) {
            cypressContainer.start();
            CypressTestResults testResults = cypressContainer.getTestResults();
            assertThat(testResults.getNumberOfFailingTests()).describedAs("%s", testResults).isZero();
        }
    }
}
