package io.jsonassertx;

/**
 * Assertion interface for object-specific operations.
 * <p>
 * Provides fluent assertion methods for validating JSON object structure.
 * Supports key existence checks and object size validation.
 * All methods return {@code this} to enable method chaining.
 * </p>
 *
 * <h3>Usage Examples</h3>
 * <pre>{@code
 * String json = "{\"user\": {\"name\": \"John\", \"age\": 30, \"email\": \"john@example.com\"}}";
 *
 * // Single key check
 * JsonAssertX.assertThat(json)
 *     .path("$.user").asObject()
 *     .hasKey("name");
 *
 * // Multiple keys
 * JsonAssertX.assertThat(json)
 *     .path("$.user").asObject()
 *     .hasKeys("name", "age", "email");
 *
 * // Absence check
 * JsonAssertX.assertThat(json)
 *     .path("$.user").asObject()
 *     .doesNotHaveKey("password");
 *
 * // Size validation
 * JsonAssertX.assertThat(json)
 *     .path("$.user").asObject()
 *     .hasKeyCount(3);
 *
 * // Combined with other assertions
 * JsonAssertX.assertThat(json)
 *     .path("$.user").asObject()
 *     .hasKey("name")
 *     .isNotEmpty();
 * }</pre>
 *
 * @since 1.0.0
 */
public interface ObjectAssertion extends JsonAssertion {

    /**
     * Asserts that the object has the specified key.
     *
     * <pre>{@code
     * String json = "{\"user\": {\"name\": \"John\", \"age\": 30}}";
     * JsonAssertX.assertThat(json)
     *     .path("$.user").asObject()
     *     .hasKey("name");
     * }</pre>
     *
     * @param key the key to check for
     * @return this assertion object for method chaining
     * @throws AssertionError if the key does not exist
     */
    ObjectAssertion hasKey(String key);

    /**
     * Asserts that the object has all of the specified keys.
     *
     * <pre>{@code
     * String json = "{\"user\": {\"name\": \"John\", \"age\": 30, \"email\": \"john@example.com\"}}";
     * JsonAssertX.assertThat(json)
     *     .path("$.user").asObject()
     *     .hasKeys("name", "age", "email");
     * }</pre>
     *
     * @param keys the keys to check for
     * @return this assertion object for method chaining
     * @throws AssertionError if any key does not exist
     */
    ObjectAssertion hasKeys(String... keys);

    /**
     * Asserts that the object does not have the specified key.
     *
     * @param key the key that should not exist
     * @return this assertion object for method chaining
     * @throws AssertionError if the key exists
     */
    ObjectAssertion doesNotHaveKey(String key);

    /**
     * Asserts that the object has exactly the specified number of keys.
     *
     * <pre>{@code
     * String json = "{\"user\": {\"name\": \"John\", \"age\": 30, \"email\": \"john@example.com\"}}";
     * JsonAssertX.assertThat(json)
     *     .path("$.user").asObject()
     *     .hasKeyCount(3);
     * }</pre>
     *
     * @param count the expected number of keys
     * @return this assertion object for method chaining
     * @throws AssertionError if the count does not match
     */
    ObjectAssertion hasKeyCount(int count);
}
