package io.jsonassertx;

import io.jsonassertx.impl.JsonAssertionImpl;

/**
 * Entry point for JSON assertions using a fluent DSL.
 * <p>
 * This class provides the main {@link #assertThat(String)} method to start JSON assertions.
 * </p>
 *
 * <h2>Basic Usage</h2>
 * <pre>{@code
 * String json = "{\"name\": \"John\", \"age\": 30}";
 * 
 * JsonAssertX.assertThat(json)
 *     .hasPath("$.name")
 *     .hasValue("$.age", 30);
 * }</pre>
 *
 * <h2>String Assertions</h2>
 * <pre>{@code
 * JsonAssertX.assertThat(json)
 *     .path("$.name").isString()
 *         .isEqualTo("John")
 *         .startsWith("J")
 *         .hasLength(4);
 * }</pre>
 *
 * <h2>Number Assertions</h2>
 * <pre>{@code
 * JsonAssertX.assertThat(json)
 *     .path("$.age").isNumber()
 *         .isGreaterThan(18)
 *         .isLessThan(100)
 *         .isBetween(25, 35);
 * }</pre>
 *
 * <h2>Array Assertions</h2>
 * <pre>{@code
 * String json = "{\"users\": [\"John\", \"Jane\", \"Bob\"]}";
 * 
 * JsonAssertX.assertThat(json)
 *     .path("$.users").isArray()
 *         .hasSize(3)
 *         .contains("John")
 *         .doesNotContain("Alice");
 * }</pre>
 *
 * <h2>Object Assertions</h2>
 * <pre>{@code
 * JsonAssertX.assertThat(json)
 *     .isObject()
 *         .hasKeys("name", "age")
 *         .doesNotHaveKey("password");
 * }</pre>
 *
 * @since 1.0.0
 */
public final class JsonAssertX {

    private JsonAssertX() {
        // Utility class - no instantiation
    }

    /**
     * Creates a new JSON assertion for the specified JSON string.
     * <p>
     * This is the main entry point for all JSON assertions.
     * </p>
     *
     * @param json the JSON string to assert against
     * @return a new {@link JsonAssertion} for the given JSON
     * @throws IllegalArgumentException if json is null
     * @throws com.fasterxml.jackson.core.JsonProcessingException if json is not valid JSON
     */
    public static JsonAssertion assertThat(String json) {
        if (json == null) {
            throw new IllegalArgumentException("JSON string cannot be null");
        }
        return new JsonAssertionImpl(json);
    }

    /**
     * Creates a new JSON assertion for the specified JSON object.
     * <p>
     * Accepts any object that will be serialized to JSON using Jackson.
     * </p>
     *
     * @param jsonObject the object to convert to JSON and assert against
     * @return a new {@link JsonAssertion} for the given object
     * @throws IllegalArgumentException if jsonObject is null
     * @throws com.fasterxml.jackson.core.JsonProcessingException if object cannot be serialized
     */
    public static JsonAssertion assertThat(Object jsonObject) {
        if (jsonObject == null) {
            throw new IllegalArgumentException("JSON object cannot be null");
        }
        return new JsonAssertionImpl(jsonObject);
    }
}
