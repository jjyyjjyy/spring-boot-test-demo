package com.example.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.IntStream;
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

    static IntStream generateInts() {
        return IntStream.range(1, 10);
    }

    static Stream<Arguments> generateArguments() {
        return Stream.of(
            Arguments.of("a", 1),
            Arguments.of("aa", 2),
            Arguments.of("aaa", 3)
        );
    }

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

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6})
    void testValueSource(int i) {
        assertTrue(i > 0);
    }

    @ParameterizedTest
    @NullSource
    void testNullString(String nullableString) {
        assertNull(nullableString);
    }

    @ParameterizedTest
    @EmptySource
    void testEmptyString(String str) {
        assertEquals(0, str.length());
    }

    @ParameterizedTest
    @EmptySource
    void testEmptyList(List<String> list) {
        assertSame(Collections.emptyList(), list);
        assertEquals(0, list.size());
    }

    @ParameterizedTest
    @EmptySource
    void testEmptySet(Set<String> set) {
        assertSame(Collections.emptySet(), set);
        assertEquals(0, set.size());
    }

    @ParameterizedTest
    @EmptySource
    void testEmptyMap(Map<String, Object> map) {
        assertSame(Collections.emptyMap(), map);
        assertEquals(0, map.size());
    }

    @ParameterizedTest
    @EmptySource
    void testEmptyArray(int[] arr) {
        assertEquals(0, arr.length);
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestStream() {
        return Stream.of("a", "b", "c")
            .map(text -> DynamicTest.dynamicTest(text, () -> assertTrue(true)));
    }

    @ParameterizedTest
    @EnumSource
    void testEnumSource(Gender gender) {
        assertTrue(Arrays.stream(Gender.class.getEnumConstants()).anyMatch(e -> e == gender));
    }

    @ParameterizedTest
    @MethodSource("generateInts")
    void testIntMethodSource(int i) {
        assertTrue(i > 0 && i < 10);
    }

    @ParameterizedTest
    @MethodSource("generateArguments")
    void testArgumentsMethodSource(String str, int i) {
        assertEquals(i, str.length());
    }

    @ParameterizedTest
    @CsvSource({"a,1", "aa,2", "aaa,3"})
    void testCsvSource(String str, int i) {
        assertEquals(i, str.length());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/str.csv")
    void testCsvFileSource(String str, int i) {
        assertEquals(i, str.length());
    }

    @ParameterizedTest
    @ArgumentsSource(SequenceArgumentProvider.class)
    void testArgumentsSource(int i) {
        assertTrue(i > 0 && i < 10);
    }

    @ParameterizedTest
    @CsvSource({"'1,3,2', '1,2,3'"})
    void testConverter(@ConvertWith(ToArrayArgumentConverter.class) int[] arr,
                       @ConvertWith(ToArrayArgumentConverter.class) int[] expect) {
        Arrays.sort(arr);
        assertArrayEquals(expect, arr);
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5", "6"})
    void testWithArgumentsAccessor(@AggregateWith(ToIntArgumentsAggregator.class) int i) {
        assertTrue(i > 0);
    }

    @RepeatedTest(3)
    void testWithRepeatedTest() {
        assertTrue(true);
    }

    public enum Gender {
        MALE, FEMALE, UNKNOWN
    }

    public static class ToIntArgumentsAggregator implements ArgumentsAggregator {

        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return accessor.getInteger(0);
        }
    }

    public static class ToArrayArgumentConverter implements ArgumentConverter {

        @Override
        public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
            Class<?> type = context.getParameter().getType();
            String[] strings = source.toString().split("\\s*,\\s*");
            if (int[].class == type) {
                return Arrays.stream(strings).mapToInt(Integer::valueOf).toArray();
            }
            return strings;
        }
    }

    public static class SequenceArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<Arguments> provideArguments(ExtensionContext context) throws Exception {
            return IntStream.range(1, 10).mapToObj(Arguments::of);
        }
    }

}
