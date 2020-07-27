package com.example.service;

import com.example.domain.User;
import com.example.domain.enumerations.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.UUID;

/**
 * @author jy
 */
@SpringBootTest
@ActiveProfiles("test")
@Sql("/test-schema.sql")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testAdd() {
        User user = new User()
            .setUsername(UUID.randomUUID().toString())
            .setAddress("los")
            .setGender(Gender.FEMALE)
            .setCreatedAt(Instant.now())
            .setUpdatedAt(Instant.now());

        userService.save(user);

        Assertions.assertEquals(user, userService.getById(user.getId()));
    }

}
