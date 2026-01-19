package io.jsonassertx;

/**
 * Assertion interface for boolean-specific operations.
 *
 * @since 1.0.0
 */
public interface BooleanAssertion extends JsonAssertion {

    /**
     * Asserts that the boolean value is true.
     *
     * @return this assertion object for method chaining
     * @throws AssertionError if the value is not true
     */
    BooleanAssertion isTrue();

    /**
     * Asserts that the boolean value is false.
     *
     * @return this assertion object for method chaining
     * @throws AssertionError if the value is not false
     */
    BooleanAssertion isFalse();
}
