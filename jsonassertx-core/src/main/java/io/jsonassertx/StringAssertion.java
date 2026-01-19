package io.jsonassertx;

/**
 * Assertion interface for string-specific operations.
 * <p>
 * Provides fluent assertion methods for validating JSON string values.
 * All methods return {@code this} to enable method chaining.
 * </p>
 *
 * <h3>Usage Examples</h3>
 * <pre>{@code
 * String json = "{\"email\": \"user@example.com\", \"status\": \"ACTIVE\"}";
 *
 * // Exact match
 * JsonAssertX.assertThat(json)
 *     .path("$.status").asString()
 *     .isEqualTo("ACTIVE");
 *
 * // Substring checks
 * JsonAssertX.assertThat(json)
 *     .path("$.email").asString()
 *     .contains("@example.com")
 *     .startsWith("user")
 *     .endsWith(".com");
 *
 * // Pattern matching
 * JsonAssertX.assertThat(json)
 *     .path("$.email").asString()
 *     .matches("^[a-z]+@[a-z]+\\.[a-z]+$");
 *
 * // Length validation
 * JsonAssertX.assertThat(json)
 *     .path("$.status").asString()
 *     .hasLength(6);
 * }</pre>
 *
 * @since 1.0.0
 */
public interface StringAssertion extends JsonAssertion {

    /**
     * Asserts that the string is equal to the expected value.
     *
     * <pre>{@code
     * String json = "{\"name\": \"John Doe\"}";
     * JsonAssertX.assertThat(json)
     *     .path("$.name").asString()
     *     .isEqualTo("John Doe");
     * }</pre>
     *
     * @param expected the expected string value
     * @return this assertion object for method chaining
     * @throws AssertionError if the strings are not equal
     */
    StringAssertion isEqualTo(String expected);

    /**
     * Asserts that the string contains the specified substring.
     *
     * <pre>{@code
     * String json = "{\"email\": \"user@example.com\"}";
     * JsonAssertX.assertThat(json)
     *     .path("$.email").asString()
     *     .contains("@example.com");
     * }</pre>
     *
     * @param substring the substring to search for
     * @return this assertion object for method chaining
     * @throws AssertionError if the substring is not found
     */
    StringAssertion contains(String substring);

    /**
     * Asserts that the string starts with the specified prefix.
     *
     * <pre>{@code
     * String json = "{\"url\": \"https://api.example.com\"}";
     * JsonAssertX.assertThat(json)
     *     .path("$.url").asString()
     *     .startsWith("https://");
     * }</pre>
     *
     * @param prefix the expected prefix
     * @return this assertion object for method chaining
     * @throws AssertionError if the string does not start with the prefix
     */
    StringAssertion startsWith(String prefix);

    /**
     * Asserts that the string ends with the specified suffix.
     *
     * <pre>{@code
     * String json = "{\"filename\": \"report.pdf\"}";
     * JsonAssertX.assertThat(json)
     *     .path("$.filename").asString()
     *     .endsWith(".pdf");
     * }</pre>
     *
     * @param suffix the expected suffix
     * @return this assertion object for method chaining
     * @throws AssertionError if the string does not end with the suffix
     */
    StringAssertion endsWith(String suffix);

    /**
     * Asserts that the string matches the specified regular expression.
     *
     * <pre>{@code
     * String json = "{\"email\": \"user@example.com\"}";
     * JsonAssertX.assertThat(json)
     *     .path("$.email").asString()
     *     .matches("^[a-z]+@[a-z]+\\.[a-z]+$");
     * }</pre>
     *
     * @param regex the regular expression pattern
     * @return this assertion object for method chaining
     * @throws AssertionError if the string does not match the pattern
     */
    StringAssertion matches(String regex);

    /**
     * Asserts that the string has the specified length.
     *
     * <pre>{@code
     * String json = "{\"code\": \"ABC123\"}";
     * JsonAssertX.assertThat(json)
     *     .path("$.code").asString()
     *     .hasLength(6);
     * }</pre>
     *
     * @param length the expected length
     * @return this assertion object for method chaining
     * @throws AssertionError if the length does not match
     */
    StringAssertion hasLength(int length);
}
