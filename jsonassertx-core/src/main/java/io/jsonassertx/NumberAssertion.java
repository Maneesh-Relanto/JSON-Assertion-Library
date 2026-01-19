package io.jsonassertx;

/**
 * Assertion interface for number-specific operations.
 * <p>
 * Provides fluent assertion methods for validating JSON numeric values.
 * Supports integers, floats, doubles, and all Java Number types.
 * All methods return {@code this} to enable method chaining.
 * </p>
 *
 * <h3>Usage Examples</h3>
 * <pre>{@code
 * String json = "{\"age\": 30, \"score\": 95.5, \"balance\": -10.25}";
 *
 * // Exact comparison
 * JsonAssertX.assertThat(json)
 *     .path("$.age").asNumber()
 *     .isEqualTo(30);
 *
 * // Relational comparisons
 * JsonAssertX.assertThat(json)
 *     .path("$.score").asNumber()
 *     .isGreaterThan(90)
 *     .isLessThanOrEqualTo(100);
 *
 * // Range validation
 * JsonAssertX.assertThat(json)
 *     .path("$.age").asNumber()
 *     .isBetween(18, 65);
 *
 * // Sign checks
 * JsonAssertX.assertThat(json)
 *     .path("$.balance").asNumber()
 *     .isNegative();
 *
 * JsonAssertX.assertThat(json)
 *     .path("$.score").asNumber()
 *     .isPositive();
 * }</pre>
 *
 * @since 1.0.0
 */
public interface NumberAssertion extends JsonAssertion {

    /**
     * Asserts that the number is equal to the expected value.
     *
     * <pre>{@code
     * String json = "{\"age\": 25}";
     * JsonAssertX.assertThat(json)
     *     .path("$.age").asNumber()
     *     .isEqualTo(25);
     * }</pre>
     *
     * @param expected the expected number value
     * @return this assertion object for method chaining
     * @throws AssertionError if the numbers are not equal
     */
    NumberAssertion isEqualTo(Number expected);

    /**
     * Asserts that the number is greater than the specified value.
     *
     * <pre>{@code
     * String json = "{\"score\": 95}";
     * JsonAssertX.assertThat(json)
     *     .path("$.score").asNumber()
     *     .isGreaterThan(90);
     * }</pre>
     *
     * @param value the value to compare against
     * @return this assertion object for method chaining
     * @throws AssertionError if the number is not greater
     */
    NumberAssertion isGreaterThan(Number value);

    /**
     * Asserts that the number is greater than or equal to the specified value.
     *
     * @param value the value to compare against
     * @return this assertion object for method chaining
     * @throws AssertionError if the number is not greater than or equal
     */
    NumberAssertion isGreaterThanOrEqualTo(Number value);

    /**
     * Asserts that the number is less than the specified value.
     *
     * @param value the value to compare against
     * @return this assertion object for method chaining
     * @throws AssertionError if the number is not less
     */
    NumberAssertion isLessThan(Number value);

    /**
     * Asserts that the number is less than or equal to the specified value.
     *
     * @param value the value to compare against
     * @return this assertion object for method chaining
     * @throws AssertionError if the number is not less than or equal
     */
    NumberAssertion isLessThanOrEqualTo(Number value);

    /**
     * Asserts that the number is between the specified range (inclusive).
     *
     * <pre>{@code
     * String json = "{\"age\": 30}";
     * JsonAssertX.assertThat(json)
     *     .path("$.age").asNumber()
     *     .isBetween(18, 65);
     * }</pre>
     *
     * @param start the start of the range (inclusive)
     * @param end the end of the range (inclusive)
     * @return this assertion object for method chaining
     * @throws AssertionError if the number is not in range
     */
    NumberAssertion isBetween(Number start, Number end);

    /**
     * Asserts that the number is positive (greater than zero).
     *
     * <pre>{@code
     * String json = "{\"balance\": 150.50}";
     * JsonAssertX.assertThat(json)
     *     .path("$.balance").asNumber()
     *     .isPositive();
     * }</pre>
     *
     * @return this assertion object for method chaining
     * @throws AssertionError if the number is not positive
     */
    NumberAssertion isPositive();

    /**
     * Asserts that the number is negative (less than zero).
     *
     * @return this assertion object for method chaining
     * @throws AssertionError if the number is not negative
     */
    NumberAssertion isNegative();

    /**
     * Asserts that the number is zero.
     *
     * @return this assertion object for method chaining
     * @throws AssertionError if the number is not zero
     */
    NumberAssertion isZero();
}
