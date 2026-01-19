package io.jsonassertx;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ArrayAssertion}.
 */
@DisplayName("Array Assertion Tests")
class ArrayAssertionTest {

    @Test
    @DisplayName("Should validate array has expected size")
    void shouldValidateArraySize() {
        String json = "{\"users\": [\"John\", \"Jane\", \"Bob\"]}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.users").isArray()
                .hasSize(3)
        );
    }

    @Test
    @DisplayName("Should fail when array size does not match")
    void shouldFailWhenArraySizeDoesNotMatch() {
        String json = "{\"users\": [\"John\", \"Jane\"]}";
        
        AssertionError error = assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.users").isArray()
                .hasSize(3)
        );
        
        assertTrue(error.getMessage().contains("3"));
        assertTrue(error.getMessage().contains("2"));
    }

    @Test
    @DisplayName("Should validate array contains element")
    void shouldValidateArrayContainsElement() {
        String json = "{\"tags\": [\"java\", \"json\", \"testing\"]}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.tags").isArray()
                .contains("java")
        );
    }

    @Test
    @DisplayName("Should fail when array does not contain element")
    void shouldFailWhenArrayDoesNotContainElement() {
        String json = "{\"tags\": [\"java\", \"json\"]}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.tags").isArray()
                .contains("python")
        );
    }

    @Test
    @DisplayName("Should validate array contains all elements")
    void shouldValidateArrayContainsAllElements() {
        String json = "{\"numbers\": [1, 2, 3, 4, 5]}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.numbers").isArray()
                .containsAll(1, 3, 5)
        );
    }

    @Test
    @DisplayName("Should fail when array does not contain all elements")
    void shouldFailWhenArrayDoesNotContainAllElements() {
        String json = "{\"numbers\": [1, 2, 3]}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.numbers").isArray()
                .containsAll(1, 2, 5)
        );
    }

    @Test
    @DisplayName("Should validate array does not contain element")
    void shouldValidateArrayDoesNotContainElement() {
        String json = "{\"users\": [\"John\", \"Jane\"]}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.users").isArray()
                .doesNotContain("Bob")
        );
    }

    @Test
    @DisplayName("Should fail when array contains unwanted element")
    void shouldFailWhenArrayContainsUnwantedElement() {
        String json = "{\"users\": [\"John\", \"Jane\", \"Bob\"]}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.users").isArray()
                .doesNotContain("Bob")
        );
    }

    @Test
    @DisplayName("Should validate all elements match predicate")
    void shouldValidateAllElementsMatch() {
        String json = "{\"scores\": [80, 85, 90, 95]}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.scores").isArray()
                .allMatch(score -> ((Number) score).intValue() >= 80)
        );
    }

    @Test
    @DisplayName("Should fail when not all elements match predicate")
    void shouldFailWhenNotAllElementsMatch() {
        String json = "{\"scores\": [70, 85, 90]}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.scores").isArray()
                .allMatch(score -> ((Number) score).intValue() >= 80)
        );
    }

    @Test
    @DisplayName("Should validate any element matches predicate")
    void shouldValidateAnyElementMatches() {
        String json = "{\"scores\": [70, 85, 60]}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.scores").isArray()
                .anyMatch(score -> ((Number) score).intValue() >= 80)
        );
    }

    @Test
    @DisplayName("Should fail when no element matches predicate")
    void shouldFailWhenNoElementMatches() {
        String json = "{\"scores\": [50, 60, 70]}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.scores").isArray()
                .anyMatch(score -> ((Number) score).intValue() >= 80)
        );
    }

    @Test
    @DisplayName("Should validate no element matches predicate")
    void shouldValidateNoElementMatches() {
        String json = "{\"scores\": [50, 60, 70]}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.scores").isArray()
                .noneMatch(score -> ((Number) score).intValue() >= 80)
        );
    }

    @Test
    @DisplayName("Should fail when any element matches unwanted predicate")
    void shouldFailWhenAnyElementMatchesUnwanted() {
        String json = "{\"scores\": [70, 85, 90]}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.scores").isArray()
                .noneMatch(score -> ((Number) score).intValue() >= 80)
        );
    }

    @Test
    @DisplayName("Should access array element by index")
    void shouldAccessArrayElementByIndex() {
        String json = "{\"users\": [\"John\", \"Jane\", \"Bob\"]}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.users").isArray()
                .element(1).isString()
        );
    }

    @Test
    @DisplayName("Should fail when array index is out of bounds")
    void shouldFailWhenArrayIndexOutOfBounds() {
        String json = "{\"users\": [\"John\", \"Jane\"]}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.users").isArray()
                .element(5)
        );
    }

    @Test
    @DisplayName("Should validate empty array")
    void shouldValidateEmptyArray() {
        String json = "{\"items\": []}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.items").isArray()
                .hasSize(0)
                .isEmpty()
        );
    }

    @Test
    @DisplayName("Should validate non-empty array")
    void shouldValidateNonEmptyArray() {
        String json = "{\"items\": [1, 2, 3]}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.items").isArray()
                .isNotEmpty()
        );
    }

    @Test
    @DisplayName("Should chain multiple array assertions")
    void shouldChainMultipleArrayAssertions() {
        String json = "{\"tags\": [\"java\", \"json\", \"testing\", \"assertions\"]}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.tags").isArray()
                .hasSize(4)
                .contains("java")
                .contains("testing")
                .doesNotContain("python")
                .isNotEmpty()
        );
    }

    @Test
    @DisplayName("Should validate array of numbers")
    void shouldValidateArrayOfNumbers() {
        String json = "{\"prices\": [9.99, 19.99, 29.99]}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.prices").isArray()
                .hasSize(3)
                .allMatch(price -> ((Number) price).doubleValue() > 0)
        );
    }
}
