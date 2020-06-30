package com.example.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * @author jy
 */
@Slf4j
@DisplayName("🤓")
class JUnitApiTest {

    private int i;

    @Test
    void testIfMac() {
        assumeTrue("Dummy OS".equals(System.getProperty("os.name")), "Running not at dummy os :)");
        fail("BOOM");
    }

    @Test
    @Disabled
    void testDisabled() {
        fail("BOOM");
    }

    @Test
    void testPerMethod1() {
        i++;
        assertEquals(1, i);
    }

    @Test
    void testPerMethod2() {
        assertEquals(0, i);
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestStream() {
        return Stream.of("a", "b", "c")
            .map(text -> DynamicTest.dynamicTest(text, () -> assertTrue(true)));
    }
}
