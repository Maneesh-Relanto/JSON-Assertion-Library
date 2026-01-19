package io.jsonassertx;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonAssertX} entry point.
 */
@DisplayName("JsonAssertX Entry Point Tests")
class JsonAssertXTest {

    @Test
    @DisplayName("Should create assertion from valid JSON string")
    void shouldCreateAssertionFromValidJsonString() {
        String json = "{\"name\": \"John\"}";
        
        assertDoesNotThrow(() -> JsonAssertX.assertThat(json));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for null JSON string")
    void shouldThrowForNullJsonString() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> JsonAssertX.assertThat((String) null)
        );
        
        assertEquals("JSON string cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for invalid JSON")
    void shouldThrowForInvalidJson() {
        String invalidJson = "{invalid json}";
        
        assertThrows(IllegalArgumentException.class, () -> JsonAssertX.assertThat(invalidJson));
    }

    @Test
    @DisplayName("Should create assertion from object")
    void shouldCreateAssertionFromObject() {
        TestUser user = new TestUser("John", 30);
        
        assertDoesNotThrow(() -> JsonAssertX.assertThat(user));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for null object")
    void shouldThrowForNullObject() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> JsonAssertX.assertThat((Object) null)
        );
        
        assertEquals("JSON object cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should validate basic JSON path exists")
    void shouldValidateJsonPathExists() {
        String json = "{\"user\": {\"name\": \"John\"}}";
        
        assertDoesNotThrow(() -> 
            JsonAssertX.assertThat(json).hasPath("$.user.name")
        );
    }

    @Test
    @DisplayName("Should fail when JSON path does not exist")
    void shouldFailWhenJsonPathDoesNotExist() {
        String json = "{\"user\": {\"name\": \"John\"}}";
        
        AssertionError error = assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json).hasPath("$.user.age")
        );
        
        assertTrue(error.getMessage().contains("$.user.age"));
    }

    @Test
    @DisplayName("Should validate JSON value equals expected")
    void shouldValidateJsonValueEquals() {
        String json = "{\"name\": \"John\", \"age\": 30}";
        
        assertDoesNotThrow(() -> 
            JsonAssertX.assertThat(json)
                .hasValue("$.name", "John")
                .hasValue("$.age", 30)
        );
    }

    @Test
    @DisplayName("Should validate JSON is not null")
    void shouldValidateJsonIsNotNull() {
        String json = "{\"name\": \"John\"}";
        
        assertDoesNotThrow(() -> JsonAssertX.assertThat(json).isNotNull());
    }

    @Test
    @DisplayName("Should validate JSON is not empty")
    void shouldValidateJsonIsNotEmpty() {
        String json = "{\"name\": \"John\"}";
        
        assertDoesNotThrow(() -> JsonAssertX.assertThat(json).isNotEmpty());
    }

    @Test
    @DisplayName("Should fail when JSON is empty")
    void shouldFailWhenJsonIsEmpty() {
        String json = "{}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json).isNotEmpty()
        );
    }

    @Test
    @DisplayName("Should validate empty JSON")
    void shouldValidateEmptyJson() {
        String json = "{}";
        
        assertDoesNotThrow(() -> JsonAssertX.assertThat(json).isEmpty());
    }

    @Test
    @DisplayName("Should chain multiple assertions")
    void shouldChainMultipleAssertions() {
        String json = "{\"name\": \"John\", \"age\": 30, \"active\": true}";
        
        assertDoesNotThrow(() -> 
            JsonAssertX.assertThat(json)
                .hasPath("$.name")
                .hasPath("$.age")
                .hasPath("$.active")
                .isNotNull()
                .isNotEmpty()
        );
    }

    // Test helper class
    static class TestUser {
        public String name;
        public int age;

        public TestUser(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
