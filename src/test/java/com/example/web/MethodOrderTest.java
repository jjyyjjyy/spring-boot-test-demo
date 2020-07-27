package com.example.web;

import org.junit.jupiter.api.*;

/**
 * @author jy
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MethodOrderTest {

    private int count;

    @BeforeEach
    void beforeEach() {
        count++;
    }

    @Test
    @Order(2)
    void test2() {
        Assertions.assertEquals(2, count);
    }

    @Test
    @Order(1)
    void test1() {
        Assertions.assertEquals(1, count);
    }
}
