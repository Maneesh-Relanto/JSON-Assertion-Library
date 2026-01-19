package io.jsonassertx;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link StringAssertion}.
 */
@DisplayName("String Assertion Tests")
class StringAssertionTest {

    @Test
    @DisplayName("Should validate string equals expected value")
    void shouldValidateStringEquals() {
        String json = "{\"name\": \"John Doe\"}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.name").isString()
                .isEqualTo("John Doe")
        );
    }

    @Test
    @DisplayName("Should fail when string does not equal expected")
    void shouldFailWhenStringNotEqual() {
        String json = "{\"name\": \"John Doe\"}";
        
        AssertionError error = assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.name").isString()
                .isEqualTo("Jane Doe")
        );
        
        assertTrue(error.getMessage().contains("John Doe"));
        assertTrue(error.getMessage().contains("Jane Doe"));
    }

    @Test
    @DisplayName("Should validate string contains substring")
    void shouldValidateStringContains() {
        String json = "{\"email\": \"john@example.com\"}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.email").isString()
                .contains("@example.com")
        );
    }

    @Test
    @DisplayName("Should fail when string does not contain substring")
    void shouldFailWhenStringDoesNotContain() {
        String json = "{\"email\": \"john@example.com\"}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.email").isString()
                .contains("@gmail.com")
        );
    }

    @Test
    @DisplayName("Should validate string starts with prefix")
    void shouldValidateStringStartsWith() {
        String json = "{\"url\": \"https://example.com\"}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.url").isString()
                .startsWith("https://")
        );
    }

    @Test
    @DisplayName("Should fail when string does not start with prefix")
    void shouldFailWhenStringDoesNotStartWith() {
        String json = "{\"url\": \"http://example.com\"}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.url").isString()
                .startsWith("https://")
        );
    }

    @Test
    @DisplayName("Should validate string ends with suffix")
    void shouldValidateStringEndsWith() {
        String json = "{\"filename\": \"document.pdf\"}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.filename").isString()
                .endsWith(".pdf")
        );
    }

    @Test
    @DisplayName("Should fail when string does not end with suffix")
    void shouldFailWhenStringDoesNotEndWith() {
        String json = "{\"filename\": \"document.pdf\"}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.filename").isString()
                .endsWith(".docx")
        );
    }

    @Test
    @DisplayName("Should validate string matches regex")
    void shouldValidateStringMatchesRegex() {
        String json = "{\"phone\": \"123-456-7890\"}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.phone").isString()
                .matches("\\d{3}-\\d{3}-\\d{4}")
        );
    }

    @Test
    @DisplayName("Should fail when string does not match regex")
    void shouldFailWhenStringDoesNotMatchRegex() {
        String json = "{\"phone\": \"123-4567\"}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.phone").isString()
                .matches("\\d{3}-\\d{3}-\\d{4}")
        );
    }

    @Test
    @DisplayName("Should validate string length")
    void shouldValidateStringLength() {
        String json = "{\"code\": \"ABCD\"}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.code").isString()
                .hasLength(4)
        );
    }

    @Test
    @DisplayName("Should fail when string length does not match")
    void shouldFailWhenStringLengthDoesNotMatch() {
        String json = "{\"code\": \"ABCD\"}";
        
        AssertionError error = assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.code").isString()
                .hasLength(5)
        );
        
        assertTrue(error.getMessage().contains("5"));
        assertTrue(error.getMessage().contains("4"));
    }

    @Test
    @DisplayName("Should chain multiple string assertions")
    void shouldChainMultipleStringAssertions() {
        String json = "{\"email\": \"john.doe@example.com\"}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.email").isString()
                .contains("@")
                .endsWith(".com")
                .startsWith("john")
                .matches("^[a-z.]+@[a-z.]+\\.[a-z]+$")
        );
    }

    @Test
    @DisplayName("Should validate empty string")
    void shouldValidateEmptyString() {
        String json = "{\"value\": \"\"}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.value").isString()
                .hasLength(0)
        );
    }
}
