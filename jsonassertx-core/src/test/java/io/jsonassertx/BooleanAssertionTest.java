package io.jsonassertx;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link BooleanAssertion}.
 */
@DisplayName("Boolean Assertion Tests")
class BooleanAssertionTest {

    @Test
    @DisplayName("Should validate boolean is true")
    void shouldValidateBooleanIsTrue() {
        String json = "{\"active\": true}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.active").isBoolean()
                .isTrue()
        );
    }

    @Test
    @DisplayName("Should fail when boolean is not true")
    void shouldFailWhenBooleanNotTrue() {
        String json = "{\"active\": false}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.active").isBoolean()
                .isTrue()
        );
    }

    @Test
    @DisplayName("Should validate boolean is false")
    void shouldValidateBooleanIsFalse() {
        String json = "{\"deleted\": false}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.deleted").isBoolean()
                .isFalse()
        );
    }

    @Test
    @DisplayName("Should fail when boolean is not false")
    void shouldFailWhenBooleanNotFalse() {
        String json = "{\"deleted\": true}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.deleted").isBoolean()
                .isFalse()
        );
    }

    @Test
    @DisplayName("Should validate multiple boolean fields")
    void shouldValidateMultipleBooleanFields() {
        String json = "{\"active\": true, \"verified\": true, \"deleted\": false}";
        
        assertDoesNotThrow(() -> {
            JsonAssertX.assertThat(json).path("$.active").isBoolean().isTrue();
            JsonAssertX.assertThat(json).path("$.verified").isBoolean().isTrue();
            JsonAssertX.assertThat(json).path("$.deleted").isBoolean().isFalse();
        });
    }

    @Test
    @DisplayName("Should fail when type is not boolean")
    void shouldFailWhenTypeNotBoolean() {
        String json = "{\"value\": \"true\"}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.value").isBoolean()
        );
    }

    @Test
    @DisplayName("Should validate nested boolean values")
    void shouldValidateNestedBooleanValues() {
        String json = "{\"user\": {\"status\": {\"active\": true}}}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.user.status.active").isBoolean()
                .isTrue()
        );
    }
}
