package io.jsonassertx;

/**
 * Assertion interface for boolean-specific operations.
 * <p>
 * Provides fluent assertion methods for validating JSON boolean values.
 * All methods return {@code this} to enable method chaining.
 * </p>
 *
 * <h3>Usage Examples</h3>
 * <pre>{@code
 * String json = "{\"active\": true, \"deleted\": false, \"verified\": true}";
 *
 * // Assert true
 * JsonAssertX.assertThat(json)
 *     .path("$.active").asBoolean()
 *     .isTrue();
 *
 * // Assert false
 * JsonAssertX.assertThat(json)
 *     .path("$.deleted").asBoolean()
 *     .isFalse();
 *
 * // Chain multiple checks
 * JsonAssertX.assertThat(json)
 *     .path("$.verified").asBoolean()
 *     .isTrue()
 *     .isNotNull();
 * }</pre>
 *
 * @since 1.0.0
 */
public interface BooleanAssertion extends JsonAssertion {

    /**
     * Asserts that the boolean value is true.
     *
     * <pre>{@code
     * String json = "{\"active\": true}";
     * JsonAssertX.assertThat(json)
     *     .path("$.active").asBoolean()
     *     .isTrue();
     * }</pre>
     *
     * @return this assertion object for method chaining
     * @throws AssertionError if the value is not true
     */
    BooleanAssertion isTrue();

    /**
     * Asserts that the boolean value is false.
     *
     * <pre>{@code
     * String json = "{\"deleted\": false}";
     * JsonAssertX.assertThat(json)
     *     .path("$.deleted").asBoolean()
     *     .isFalse();
     * }</pre>
     *
     * @return this assertion object for method chaining
     * @throws AssertionError if the value is not false
     */
    BooleanAssertion isFalse();
}
