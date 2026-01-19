package io.jsonassertx;

/**
 * Assertion interface for string-specific operations.
 *
 * @since 1.0.0
 */
public interface StringAssertion extends JsonAssertion {

    /**
     * Asserts that the string is equal to the expected value.
     *
     * @param expected the expected string value
     * @return this assertion object for method chaining
     * @throws AssertionError if the strings are not equal
     */
    StringAssertion isEqualTo(String expected);

    /**
     * Asserts that the string contains the specified substring.
     *
     * @param substring the substring to search for
     * @return this assertion object for method chaining
     * @throws AssertionError if the substring is not found
     */
    StringAssertion contains(String substring);

    /**
     * Asserts that the string starts with the specified prefix.
     *
     * @param prefix the expected prefix
     * @return this assertion object for method chaining
     * @throws AssertionError if the string does not start with the prefix
     */
    StringAssertion startsWith(String prefix);

    /**
     * Asserts that the string ends with the specified suffix.
     *
     * @param suffix the expected suffix
     * @return this assertion object for method chaining
     * @throws AssertionError if the string does not end with the suffix
     */
    StringAssertion endsWith(String suffix);

    /**
     * Asserts that the string matches the specified regular expression.
     *
     * @param regex the regular expression pattern
     * @return this assertion object for method chaining
     * @throws AssertionError if the string does not match the pattern
     */
    StringAssertion matches(String regex);

    /**
     * Asserts that the string has the specified length.
     *
     * @param length the expected length
     * @return this assertion object for method chaining
     * @throws AssertionError if the length does not match
     */
    StringAssertion hasLength(int length);
}
