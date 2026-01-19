package io.jsonassertx;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ObjectAssertion}.
 */
@DisplayName("Object Assertion Tests")
class ObjectAssertionTest {

    @Test
    @DisplayName("Should validate object has key")
    void shouldValidateObjectHasKey() {
        String json = "{\"name\": \"John\", \"age\": 30}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json).isObject()
                .hasKey("name")
        );
    }

    @Test
    @DisplayName("Should fail when object does not have key")
    void shouldFailWhenObjectDoesNotHaveKey() {
        String json = "{\"name\": \"John\"}";
        
        AssertionError error = assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json).isObject()
                .hasKey("email")
        );
        
        assertTrue(error.getMessage().contains("email"));
    }

    @Test
    @DisplayName("Should validate object has multiple keys")
    void shouldValidateObjectHasMultipleKeys() {
        String json = "{\"id\": 1, \"name\": \"John\", \"email\": \"john@example.com\"}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json).isObject()
                .hasKeys("id", "name", "email")
        );
    }

    @Test
    @DisplayName("Should fail when object missing any key")
    void shouldFailWhenObjectMissingAnyKey() {
        String json = "{\"id\": 1, \"name\": \"John\"}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json).isObject()
                .hasKeys("id", "name", "email")
        );
    }

    @Test
    @DisplayName("Should validate object does not have key")
    void shouldValidateObjectDoesNotHaveKey() {
        String json = "{\"name\": \"John\", \"email\": \"john@example.com\"}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json).isObject()
                .doesNotHaveKey("password")
        );
    }

    @Test
    @DisplayName("Should fail when object has unwanted key")
    void shouldFailWhenObjectHasUnwantedKey() {
        String json = "{\"name\": \"John\", \"password\": \"secret\"}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json).isObject()
                .doesNotHaveKey("password")
        );
    }

    @Test
    @DisplayName("Should validate object has exact key count")
    void shouldValidateObjectHasExactKeyCount() {
        String json = "{\"id\": 1, \"name\": \"John\", \"age\": 30}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json).isObject()
                .hasKeyCount(3)
        );
    }

    @Test
    @DisplayName("Should fail when object key count does not match")
    void shouldFailWhenObjectKeyCountDoesNotMatch() {
        String json = "{\"id\": 1, \"name\": \"John\"}";
        
        AssertionError error = assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json).isObject()
                .hasKeyCount(3)
        );
        
        assertTrue(error.getMessage().contains("3"));
        assertTrue(error.getMessage().contains("2"));
    }

    @Test
    @DisplayName("Should validate nested object")
    void shouldValidateNestedObject() {
        String json = "{\"user\": {\"name\": \"John\", \"age\": 30}}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.user").isObject()
                .hasKeys("name", "age")
        );
    }

    @Test
    @DisplayName("Should validate empty object")
    void shouldValidateEmptyObject() {
        String json = "{}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json).isObject()
                .hasKeyCount(0)
                .isEmpty()
        );
    }

    @Test
    @DisplayName("Should validate non-empty object")
    void shouldValidateNonEmptyObject() {
        String json = "{\"name\": \"John\"}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json).isObject()
                .isNotEmpty()
        );
    }

    @Test
    @DisplayName("Should chain multiple object assertions")
    void shouldChainMultipleObjectAssertions() {
        String json = "{\"id\": 1, \"name\": \"John\", \"email\": \"john@example.com\"}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json).isObject()
                .hasKeys("id", "name", "email")
                .doesNotHaveKey("password")
                .hasKeyCount(3)
                .isNotEmpty()
        );
    }

    @Test
    @DisplayName("Should validate complex nested structure")
    void shouldValidateComplexNestedStructure() {
        String json = "{\"user\": {\"profile\": {\"name\": \"John\", \"age\": 30}, \"settings\": {\"theme\": \"dark\"}}}";
        
        assertDoesNotThrow(() -> {
            JsonAssertX.assertThat(json)
                .path("$.user").isObject()
                .hasKeys("profile", "settings");
                
            JsonAssertX.assertThat(json)
                .path("$.user.profile").isObject()
                .hasKeys("name", "age");
                
            JsonAssertX.assertThat(json)
                .path("$.user.settings").isObject()
                .hasKey("theme");
        });
    }

    @Test
    @DisplayName("Should fail when type is not object")
    void shouldFailWhenTypeNotObject() {
        String json = "{\"items\": [1, 2, 3]}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.items").isObject()
        );
    }
}
