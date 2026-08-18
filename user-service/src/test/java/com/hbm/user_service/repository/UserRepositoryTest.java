package com.hbm.user_service.repository;

import com.hbm.user_service.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@Testcontainers // Activates automatic Docker container lifecycle management
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Prevents Spring from forcing H2
class UserRepositoryTest {

    // Defines the specific Docker image to run.
    @Container
    static PostgreSQLContainer <?> postgresContainer = new PostgreSQLContainer <>("postgres:16-alpine")
            .withDatabaseName("user-db")
            .withUsername("user")
            .withPassword("userPass");

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void should_give_correct_email_after_save() {
        // Arrange
        User testUser = new User();
        testUser.setEmail("docker@example.com");
        testUser.setPassword("super_secure_pass");

        entityManager.persistAndFlush(testUser); // Forces write to the real Docker engine

        // Act
        Optional<User> result = userRepository.findByEmail("docker@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("docker@example.com", result.get().getEmail());
    }
}

