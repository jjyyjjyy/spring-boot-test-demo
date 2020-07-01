package com.example.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

/**
 * @author jy
 */
@Slf4j
class ExtensionTest {

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

    @AfterEach
    void afterEach() {
        log.info("afterEach");
    }

    void a() {
    }

    void b() {
    }

    @Test
    void testC(@RandomInt int i) {
        log.info("test executing... {}", i);
    }
}
