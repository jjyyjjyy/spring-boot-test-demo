package com.example.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author jy
 */
@Slf4j
class LifecycleTest {

    private int count;

    @BeforeAll
    static void beforeAll() {
        log.info("beforeAll");
    }

    @AfterAll
    static void afterAll() {
        log.info("afterAll");
    }

    @BeforeEach
    void beforeEach() {
        log.info("beforeEach");
    }

    @Test
    void succeedingTest() {
        log.info("succeedingTest");
    }

    @RepeatedTest(3)
    void testPerMethod() {
        assertEquals(0, count++);
    }

    @AfterEach
    void afterEach() {
        log.info("afterEach");
    }
}
