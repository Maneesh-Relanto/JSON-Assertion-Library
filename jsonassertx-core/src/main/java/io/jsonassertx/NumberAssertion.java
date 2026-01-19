package io.jsonassertx;

/**
 * Assertion interface for number-specific operations.
 *
 * @since 1.0.0
 */
public interface NumberAssertion extends JsonAssertion {

    /**
     * Asserts that the number is equal to the expected value.
     *
     * @param expected the expected number value
     * @return this assertion object for method chaining
     * @throws AssertionError if the numbers are not equal
     */
    NumberAssertion isEqualTo(Number expected);

    /**
     * Asserts that the number is greater than the specified value.
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
     * @param start the start of the range (inclusive)
     * @param end the end of the range (inclusive)
     * @return this assertion object for method chaining
     * @throws AssertionError if the number is not in range
     */
    NumberAssertion isBetween(Number start, Number end);

    /**
     * Asserts that the number is positive (greater than zero).
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
