package com.example.service;

import com.example.domain.User;
import com.example.domain.enumerations.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.test.context.jdbc.SqlMergeMode.MergeMode.MERGE;

/**
 * @author jy
 */
@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@Sql("/test-schema.sql")
@SqlMergeMode(MERGE)
class UserServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine")
        .withUsername("jy")
        .withPassword("123123")
        .withDatabaseName("demo");

    @Autowired
    private UserService userService;

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
    }

    @Test
    @Transactional
    void testAdd() {
        User user = new User().setUsername(UUID.randomUUID().toString())
            .setAddress("los")
            .setGender(Gender.FEMALE)
            .setCreatedAt(Instant.now())
            .setUpdatedAt(Instant.now());

        userService.save(user);

        Assertions.assertEquals(user, userService.getById(user.getId()));
    }

    @Test
    @Sql
    @Transactional
    void testList() {
        Assertions.assertEquals(2, userService.list().size());
    }

}
