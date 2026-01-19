package io.jsonassertx;

/**
 * Core fluent assertion interface for JSON validation.
 * <p>
 * This interface provides a chainable API for making assertions about JSON content.
 * Implementations should return {@code this} to enable method chaining.
 * </p>
 *
 * <h3>Basic Usage</h3>
 * <pre>{@code
 * // Simple path assertions
 * JsonAssertX.assertThat(jsonString)
 *     .hasPath("$.user.name")
 *     .hasValue("$.user.age", 25)
 *     .isNotEmpty();
 *
 * // Type-specific assertions
 * JsonAssertX.assertThat(jsonString)
 *     .path("$.email").asString()
 *     .contains("@example.com");
 *
 * JsonAssertX.assertThat(jsonString)
 *     .path("$.score").asNumber()
 *     .isGreaterThan(90);
 *
 * JsonAssertX.assertThat(jsonString)
 *     .path("$.active").asBoolean()
 *     .isTrue();
 * }</pre>
 *
 * <h3>Array Assertions</h3>
 * <pre>{@code
 * JsonAssertX.assertThat(jsonString)
 *     .path("$.tags").asArray()
 *     .hasSize(3)
 *     .contains("testing")
 *     .allMatch(tag -> ((String) tag).length() > 0);
 * }</pre>
 *
 * <h3>Object Assertions</h3>
 * <pre>{@code
 * JsonAssertX.assertThat(jsonString)
 *     .path("$.user").asObject()
 *     .hasKeys("name", "email", "age")
 *     .hasKeyCount(3);
 * }</pre>
 *
 * <h3>Nested Navigation</h3>
 * <pre>{@code
 * JsonAssertX.assertThat(jsonString)
 *     .path("$.company.employees[0].name").asString()
 *     .isEqualTo("Alice");
 *
 * // JSONPath filters
 * JsonAssertX.assertThat(jsonString)
 *     .path("$.employees[?(@.role == 'Manager')].salary").asArray()
 *     .allMatch(salary -> ((Number) salary).doubleValue() > 100000);
 * }</pre>
 *
 * @since 1.0.0
 */
public interface JsonAssertion {

    /**
     * Asserts that the JSON contains the specified path.
     *
     * <pre>{@code
     * String json = "{\"user\": {\"name\": \"John\", \"age\": 30}}";
     * JsonAssertX.assertThat(json)
     *     .hasPath("$.user.name")
     *     .hasPath("$.user.age");
     * }</pre>
     *
     * @param path the JSON path to check (using JsonPath notation)
     * @return this assertion object for method chaining
     * @throws AssertionError if the path does not exist
     * @throws IllegalArgumentException if the path is null or invalid
     */
    JsonAssertion hasPath(String path);

    /**
     * Asserts that the JSON does not contain the specified path.
     *
     * <pre>{@code
     * String json = "{\"user\": {\"name\": \"John\"}}";
     * JsonAssertX.assertThat(json)
     *     .doesNotHavePath("$.user.password");
     * }</pre>
     *
     * @param path the JSON path to check (using JsonPath notation)
     * @return this assertion object for method chaining
     * @throws AssertionError if the path exists
     * @throws IllegalArgumentException if the path is null or invalid
     */
    JsonAssertion doesNotHavePath(String path);

    /**
     * Asserts that the value at the specified path equals the expected value.
     *
     * <pre>{@code
     * String json = "{\"user\": {\"name\": \"John\", \"age\": 30}}";
     * JsonAssertX.assertThat(json)
     *     .hasValue("$.user.name", "John")
     *     .hasValue("$.user.age", 30);
     * }</pre>
     *
     * @param path the JSON path to check
     * @param expected the expected value
     * @return this assertion object for method chaining
     * @throws AssertionError if the values do not match
     */
    JsonAssertion hasValue(String path, Object expected);

    /**
     * Asserts that the JSON is not null.
     *
     * @return this assertion object for method chaining
     * @throws AssertionError if the JSON is null
     */
    JsonAssertion isNotNull();

    /**
     * Asserts that the JSON is null or the specified path points to null.
     *
     * @return this assertion object for method chaining
     * @throws AssertionError if the JSON is not null
     */
    JsonAssertion isNull();

    /**
     * Asserts that the JSON is not empty.
     * For objects: has at least one field
     * For arrays: has at least one element
     * For strings: has non-zero length
     *
     * <pre>{@code
     * String json = "{\"user\": {\"name\": \"John\"}}";
     * JsonAssertX.assertThat(json)
     *     .path("$.user").isObject()
     *     .isNotEmpty();
     * }</pre>
     *
     * @return this assertion object for method chaining
     * @throws AssertionError if the JSON is empty
     */
    JsonAssertion isNotEmpty();

    /**
     * Asserts that the JSON is empty.
     * For objects: has no fields
     * For arrays: has no elements
     * For strings: has zero length
     *
     * <pre>{@code
     * String json = "{\"items\": []}";
     * JsonAssertX.assertThat(json)
     *     .path("$.items").asArray()
     *     .isEmpty();
     * }</pre>
     *
     * @return this assertion object for method chaining
     * @throws AssertionError if the JSON is not empty
     */
    JsonAssertion isEmpty();

    /**
     * Asserts that the JSON at the current path is an object.
     *
     * <pre>{@code
     * String json = "{\"user\": {\"name\": \"John\", \"age\": 30}}";
     * JsonAssertX.assertThat(json)
     *     .path("$.user").isObject()
     *     .hasKey("name")
     *     .hasKey("age");
     * }</pre>
     *
     * @return an object assertion for further object-specific checks
     * @throws AssertionError if the JSON is not an object
     */
    ObjectAssertion isObject();

    /**
     * Treats the JSON at the current path as an object and returns object-specific assertions.
     * Alias for {@link #isObject()} with more intuitive naming.
     *
     * @return an object assertion for further object-specific checks
     * @throws AssertionError if the JSON is not an object
     * @see #isObject()
     */
    default ObjectAssertion asObject() {
        return isObject();
    }

    /**
     * Asserts that the JSON at the current path is an array.
     *
     * @return an array assertion for further array-specific checks
     * @throws AssertionError if the JSON is not an array
     */
    ArrayAssertion isArray();

    /**
     * Treats the JSON at the current path as an array and returns array-specific assertions.
     * Alias for {@link #isArray()} with more intuitive naming.
     *
     * @return an array assertion for further array-specific checks
     * @throws AssertionError if the JSON is not an array
     * @see #isArray()
     */
    default ArrayAssertion asArray() {
        return isArray();
    }

    /**
     * Asserts that the JSON at the current path is a string.
     *
     * @return a string assertion for further string-specific checks
     * @throws AssertionError if the JSON is not a string
     */
    StringAssertion isString();

    /**
     * Treats the JSON at the current path as a string and returns string-specific assertions.
     * Alias for {@link #isString()} with more intuitive naming.
     *
     * @return a string assertion for further string-specific checks
     * @throws AssertionError if the JSON is not a string
     * @see #isString()
     */
    default StringAssertion asString() {
        return isString();
    }

    /**
     * Asserts that the JSON at the current path is a number.
     *
     * @return a number assertion for further number-specific checks
     * @throws AssertionError if the JSON is not a number
     */
    NumberAssertion isNumber();

    /**
     * Treats the JSON at the current path as a number and returns number-specific assertions.
     * Alias for {@link #isNumber()} with more intuitive naming.
     *
     * @return a number assertion for further number-specific checks
     * @throws AssertionError if the JSON is not a number
     * @see #isNumber()
     */
    default NumberAssertion asNumber() {
        return isNumber();
    }

    /**
     * Asserts that the JSON at the current path is a boolean.
     *
     * @return a boolean assertion for further boolean-specific checks
     * @throws AssertionError if the JSON is not a boolean
     */
    BooleanAssertion isBoolean();

    /**
     * Treats the JSON at the current path as a boolean and returns boolean-specific assertions.
     * Alias for {@link #isBoolean()} with more intuitive naming.
     *
     * @return a boolean assertion for further boolean-specific checks
     * @throws AssertionError if the JSON is not a boolean
     * @see #isBoolean()
     */
    default BooleanAssertion asBoolean() {
        return isBoolean();
    }

    /**
     * Navigates to the specified JSON path and returns an assertion for that path.
     *
     * <pre>{@code
     * String json = "{\"company\": {\"employees\": [{\"name\": \"Alice\"}, {\"name\": \"Bob\"}]}}";
     * // Navigate to nested property
     * JsonAssertX.assertThat(json)
     *     .path("$.company.employees[0].name").asString()
     *     .isEqualTo("Alice");
     * 
     * // Use JSONPath filters
     * JsonAssertX.assertThat(json)
     *     .path("$.company.employees[?(@.name == 'Bob')].name")
     *     .asArray()
     *     .contains("Bob");
     * }</pre>
     *
     * @param path the JSON path to navigate to
     * @return a new assertion scoped to the specified path
     * @throws AssertionError if the path does not exist
     */
    JsonAssertion path(String path);
}
