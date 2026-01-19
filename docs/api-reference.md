# API Reference

Complete reference for all JSONAssertX assertion methods.

---

## Entry Point

### `JsonAssertX.assertThat(String json)`

Creates a new assertion for the given JSON string.

**Parameters:**
- `json` - JSON string to assert against

**Returns:** `JsonAssertion`

**Example:**
```java
JsonAssertX.assertThat("{\"name\": \"John\"}")
    .path("$.name").asString()
    .isEqualTo("John");
```

---

## Core Assertions (`JsonAssertion`)

### Path Navigation

#### `path(String path)`
Navigates to the specified JSONPath.

**Example:**
```java
.path("$.user.email")
.path("$.items[0].name")
.path("$.users[?(@.age > 18)]")
```

### Path Checks

#### `hasPath(String path)`
Asserts that the path exists.

**Example:**
```java
assertThat(json).hasPath("$.user.name");
```

#### `doesNotHavePath(String path)`
Asserts that the path does not exist.

**Example:**
```java
assertThat(json).doesNotHavePath("$.user.password");
```

### Value Checks

#### `hasValue(String path, Object expected)`
Asserts that the value at path equals the expected value.

**Example:**
```java
assertThat(json).hasValue("$.user.age", 30);
```

#### `isNull()`
Asserts that the value is null.

**Example:**
```java
assertThat(json).path("$.middleName").isNull();
```

#### `isNotNull()`
Asserts that the value is not null.

**Example:**
```java
assertThat(json).path("$.name").isNotNull();
```

### Empty Checks

#### `isEmpty()`
Asserts that the value is empty (object, array, or string).

**Example:**
```java
assertThat(json).path("$.items").asArray().isEmpty();
```

#### `isNotEmpty()`
Asserts that the value is not empty.

**Example:**
```java
assertThat(json).path("$.user").asObject().isNotEmpty();
```

### Type Conversions

#### `asString()`
Treats the value as a string and returns string-specific assertions.

**Example:**
```java
.path("$.email").asString().contains("@")
```

#### `asNumber()`
Treats the value as a number and returns number-specific assertions.

**Example:**
```java
.path("$.age").asNumber().isGreaterThan(18)
```

#### `asBoolean()`
Treats the value as a boolean and returns boolean-specific assertions.

**Example:**
```java
.path("$.active").asBoolean().isTrue()
```

#### `asArray()`
Treats the value as an array and returns array-specific assertions.

**Example:**
```java
.path("$.tags").asArray().hasSize(3)
```

#### `asObject()`
Treats the value as an object and returns object-specific assertions.

**Example:**
```java
.path("$.user").asObject().hasKey("email")
```

---

## String Assertions (`StringAssertion`)

### `isEqualTo(String expected)`
Asserts string equality.

**Example:**
```java
.path("$.name").asString().isEqualTo("John")
```

### `contains(String substring)`
Asserts the string contains a substring.

**Example:**
```java
.path("$.email").asString().contains("@example.com")
```

### `startsWith(String prefix)`
Asserts the string starts with a prefix.

**Example:**
```java
.path("$.url").asString().startsWith("https://")
```

### `endsWith(String suffix)`
Asserts the string ends with a suffix.

**Example:**
```java
.path("$.filename").asString().endsWith(".pdf")
```

### `matches(String regex)`
Asserts the string matches a regular expression.

**Example:**
```java
.path("$.email").asString().matches("^[a-z]+@[a-z]+\\.[a-z]+$")
```

### `hasLength(int length)`
Asserts the string has a specific length.

**Example:**
```java
.path("$.code").asString().hasLength(6)
```

---

## Number Assertions (`NumberAssertion`)

### `isEqualTo(Number expected)`
Asserts number equality.

**Example:**
```java
.path("$.age").asNumber().isEqualTo(30)
```

### `isGreaterThan(Number value)`
Asserts the number is greater than a value.

**Example:**
```java
.path("$.score").asNumber().isGreaterThan(90)
```

### `isGreaterThanOrEqualTo(Number value)`
Asserts the number is greater than or equal to a value.

**Example:**
```java
.path("$.age").asNumber().isGreaterThanOrEqualTo(18)
```

### `isLessThan(Number value)`
Asserts the number is less than a value.

**Example:**
```java
.path("$.temperature").asNumber().isLessThan(100)
```

### `isLessThanOrEqualTo(Number value)`
Asserts the number is less than or equal to a value.

**Example:**
```java
.path("$.discount").asNumber().isLessThanOrEqualTo(50)
```

### `isBetween(Number start, Number end)`
Asserts the number is between two values (inclusive).

**Example:**
```java
.path("$.age").asNumber().isBetween(18, 65)
```

### `isPositive()`
Asserts the number is greater than zero.

**Example:**
```java
.path("$.balance").asNumber().isPositive()
```

### `isNegative()`
Asserts the number is less than zero.

**Example:**
```java
.path("$.debt").asNumber().isNegative()
```

### `isZero()`
Asserts the number is zero.

**Example:**
```java
.path("$.count").asNumber().isZero()
```

---

## Boolean Assertions (`BooleanAssertion`)

### `isTrue()`
Asserts the boolean is true.

**Example:**
```java
.path("$.active").asBoolean().isTrue()
```

### `isFalse()`
Asserts the boolean is false.

**Example:**
```java
.path("$.deleted").asBoolean().isFalse()
```

---

## Array Assertions (`ArrayAssertion`)

### `hasSize(int size)`
Asserts the array has a specific size.

**Example:**
```java
.path("$.tags").asArray().hasSize(3)
```

### `contains(Object element)`
Asserts the array contains an element.

**Example:**
```java
.path("$.tags").asArray().contains("testing")
```

### `containsAll(Object... elements)`
Asserts the array contains all specified elements.

**Example:**
```java
.path("$.tags").asArray().containsAll("java", "testing", "json")
```

### `doesNotContain(Object element)`
Asserts the array does not contain an element.

**Example:**
```java
.path("$.tags").asArray().doesNotContain("python")
```

### `allMatch(Predicate<Object> predicate)`
Asserts all array elements match the predicate.

**Example:**
```java
.path("$.scores").asArray()
    .allMatch(score -> ((Number) score).intValue() >= 80)
```

### `anyMatch(Predicate<Object> predicate)`
Asserts at least one element matches the predicate.

**Example:**
```java
.path("$.scores").asArray()
    .anyMatch(score -> ((Number) score).intValue() > 90)
```

### `noneMatch(Predicate<Object> predicate)`
Asserts no elements match the predicate.

**Example:**
```java
.path("$.scores").asArray()
    .noneMatch(score -> ((Number) score).intValue() < 0)
```

### `element(int index)`
Returns an assertion for the element at the specified index.

**Example:**
```java
.path("$.users").asArray()
    .element(0).asObject()
    .hasKey("name")
```

---

## Object Assertions (`ObjectAssertion`)

### `hasKey(String key)`
Asserts the object has a specific key.

**Example:**
```java
.path("$.user").asObject().hasKey("email")
```

### `hasKeys(String... keys)`
Asserts the object has all specified keys.

**Example:**
```java
.path("$.user").asObject().hasKeys("name", "age", "email")
```

### `doesNotHaveKey(String key)`
Asserts the object does not have a key.

**Example:**
```java
.path("$.user").asObject().doesNotHaveKey("password")
```

### `hasKeyCount(int count)`
Asserts the object has a specific number of keys.

**Example:**
```java
.path("$.user").asObject().hasKeyCount(3)
```

---

## Method Chaining

All assertion methods return the assertion object, enabling fluent chaining:

```java
JsonAssertX.assertThat(json)
    .path("$.user.email").asString()
    .isNotEmpty()
    .contains("@")
    .endsWith(".com")
    .hasLength(20);
```

Multiple paths can be checked in sequence:

```java
JsonAssertX.assertThat(json)
    .path("$.user.name").asString().isEqualTo("John")
    .path("$.user.age").asNumber().isBetween(18, 65)
    .path("$.user.active").asBoolean().isTrue();
```

---

## JSONPath Support

JSONAssertX uses [JsonPath](https://github.com/json-path/JsonPath) library. All standard JSONPath expressions are supported:

### Operators

| Operator | Description |
|----------|-------------|
| `$` | Root element |
| `@` | Current element (in filters) |
| `.` | Child operator |
| `..` | Recursive descent |
| `*` | Wildcard |
| `[]` | Array subscript |
| `[,]` | Union operator |
| `[start:end]` | Array slice |
| `[?()]` | Filter expression |

### Examples

```java
// Root
.path("$")

// Property access
.path("$.user.name")

// Array access
.path("$.items[0]")
.path("$.items[1,3,5]")
.path("$.items[0:3]")

// All elements
.path("$.items[*].price")

// Recursive descent
.path("$..email")

// Filters
.path("$.users[?(@.age > 18)]")
.path("$.items[?(@.price < 100)]")
.path("$.users[?(@.name == 'John')]")
```

---

## Common Patterns

### Testing API Responses

```java
JsonAssertX.assertThat(apiResponse)
    .path("$.status").asNumber().isEqualTo(200)
    .path("$.data.id").asNumber().isPositive()
    .path("$.data.created_at").asString().matches("\\d{4}-\\d{2}-\\d{2}.*");
```

### Validating Arrays

```java
JsonAssertX.assertThat(json)
    .path("$.items").asArray()
    .hasSize(5)
    .allMatch(item -> item.asObject().hasKey("id"))
    .element(0).asObject().hasKey("name");
```

### Complex Nested Validation

```java
JsonAssertX.assertThat(json)
    .path("$.company.employees[?(@.role == 'Manager')].salary").asArray()
    .allMatch(salary -> ((Number) salary).doubleValue() > 100000);
```

---

## See Also

- [Getting Started Guide](getting-started.md)
- [FAQ](faq.md)
- [Example Code](../jsonassertx-examples/)
