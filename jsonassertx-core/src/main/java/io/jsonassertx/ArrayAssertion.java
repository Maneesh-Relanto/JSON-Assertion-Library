package io.jsonassertx;

import java.util.function.Predicate;

/**
 * Assertion interface for array-specific operations.
 *
 * @since 1.0.0
 */
public interface ArrayAssertion extends JsonAssertion {

    /**
     * Asserts that the array has the specified size.
     *
     * @param size the expected array size
     * @return this assertion object for method chaining
     * @throws AssertionError if the size does not match
     */
    ArrayAssertion hasSize(int size);

    /**
     * Asserts that the array contains the specified element.
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
     * @param predicate the predicate to test elements against
     * @return this assertion object for method chaining
     * @throws AssertionError if any element does not match
     */
    ArrayAssertion allMatch(Predicate<Object> predicate);

    /**
     * Asserts that at least one element in the array matches the given predicate.
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
