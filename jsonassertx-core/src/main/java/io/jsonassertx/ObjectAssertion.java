package io.jsonassertx;

/**
 * Assertion interface for object-specific operations.
 *
 * @since 1.0.0
 */
public interface ObjectAssertion extends JsonAssertion {

    /**
     * Asserts that the object has the specified key.
     *
     * @param key the key to check for
     * @return this assertion object for method chaining
     * @throws AssertionError if the key does not exist
     */
    ObjectAssertion hasKey(String key);

    /**
     * Asserts that the object has all of the specified keys.
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
     * @param count the expected number of keys
     * @return this assertion object for method chaining
     * @throws AssertionError if the count does not match
     */
    ObjectAssertion hasKeyCount(int count);
}
