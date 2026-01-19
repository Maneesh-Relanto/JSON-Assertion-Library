package io.jsonassertx;

import java.util.function.Predicate;

/**
 * Assertion interface for array-specific operations.
 * <p>
 * Provides fluent assertion methods for validating JSON arrays.
 * Supports size checks, element searches, and predicate-based validations.
 * All methods return {@code this} to enable method chaining.
 * </p>
 *
 * <h3>Usage Examples</h3>
 * <pre>{@code
 * String json = "{\"tags\": [\"java\", \"testing\", \"json\"], \"scores\": [85, 90, 95]}";
 *
 * // Size and containment
 * JsonAssertX.assertThat(json)
 *     .path("$.tags").asArray()
 *     .hasSize(3)
 *     .contains("testing")
 *     .doesNotContain("python");
 *
 * // Multiple elements
 * JsonAssertX.assertThat(json)
 *     .path("$.tags").asArray()
 *     .containsAll("java", "testing", "json");
 *
 * // Predicate assertions
 * JsonAssertX.assertThat(json)
 *     .path("$.scores").asArray()
 *     .allMatch(score -> (int) score >= 80)   // All scores >= 80
 *     .anyMatch(score -> (int) score > 90);   // At least one > 90
 *
 * // Element navigation
 * JsonAssertX.assertThat(json)
 *     .path("$.tags").asArray()
 *     .element(0).asString()
 *     .isEqualTo("java");
 * }</pre>
 *
 * @since 1.0.0
 */
public interface ArrayAssertion extends JsonAssertion {

    /**
     * Asserts that the array has the specified size.
     *
     * <pre>{@code
     * String json = "{\"tags\": [\"java\", \"testing\", \"json\"]}";
     * JsonAssertX.assertThat(json)
     *     .path("$.tags").asArray()
     *     .hasSize(3);
     * }</pre>
     *
     * @param size the expected array size
     * @return this assertion object for method chaining
     * @throws AssertionError if the size does not match
     */
    ArrayAssertion hasSize(int size);

    /**
     * Asserts that the array contains the specified element.
     *
     * <pre>{@code
     * String json = "{\"tags\": [\"java\", \"testing\", \"json\"]}";
     * JsonAssertX.assertThat(json)
     *     .path("$.tags").asArray()
     *     .contains("testing");
     * }</pre>
     *
     * @param element the element to search for
     * @return this assertion object for method chaining
     * @throws AssertionError if the element is not found
     */
    ArrayAssertion contains(Object element);

    /**
     * Asserts that the array contains all of the specified elements.
     *
     * @param elements the elements to search for
     * @return this assertion object for method chaining
     * @throws AssertionError if any element is not found
     */
    ArrayAssertion containsAll(Object... elements);

    /**
     * Asserts that the array does not contain the specified element.
     *
     * @param element the element that should not be present
     * @return this assertion object for method chaining
     * @throws AssertionError if the element is found
     */
    ArrayAssertion doesNotContain(Object element);

    /**
     * Asserts that all elements in the array match the given predicate.
     *
     * <pre>{@code
     * String json = "{\"scores\": [85, 90, 95]}";
     * JsonAssertX.assertThat(json)
     *     .path("$.scores").asArray()
     *     .allMatch(score -> (int) score >= 80);
     * }</pre>
     *
     * @param predicate the predicate to test elements against
     * @return this assertion object for method chaining
     * @throws AssertionError if any element does not match
     */
    ArrayAssertion allMatch(Predicate<Object> predicate);

    /**
     * Asserts that at least one element in the array matches the given predicate.
     *
     * <pre>{@code
     * String json = "{\"scores\": [85, 90, 95]}";
     * JsonAssertX.assertThat(json)
     *     .path("$.scores").asArray()
     *     .anyMatch(score -> (int) score > 90);
     * }</pre>
     *
     * @param predicate the predicate to test elements against
     * @return this assertion object for method chaining
     * @throws AssertionError if no element matches
     */
    ArrayAssertion anyMatch(Predicate<Object> predicate);

    /**
     * Asserts that no elements in the array match the given predicate.
     *
     * @param predicate the predicate to test elements against
     * @return this assertion object for method chaining
     * @throws AssertionError if any element matches
     */
    ArrayAssertion noneMatch(Predicate<Object> predicate);

    /**
     * Returns an assertion for the element at the specified index.
     *
     * @param index the array index
     * @return a new assertion scoped to the array element
     * @throws AssertionError if the index is out of bounds
     */
    JsonAssertion element(int index);
}
