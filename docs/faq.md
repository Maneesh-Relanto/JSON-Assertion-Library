# Frequently Asked Questions (FAQ)

Common questions and answers about JSONAssertX.

---

## General Questions

### What is JSONAssertX?

JSONAssertX is a fluent assertion library for testing JSON in Java. It provides an intuitive, type-safe API for validating JSON structures, values, and content with clear error messages.

### Why should I use JSONAssertX instead of other libraries?

JSONAssertX offers:
- **Fluent API** - Readable, chainable assertions
- **Type-safe** - Specific assertions for strings, numbers, booleans, arrays, and objects
- **Clear errors** - Detailed failure messages showing exactly what went wrong
- **JSONPath support** - Navigate complex nested structures easily
- **Zero bloat** - Minimal dependencies (Jackson + JSONPath)

### What Java version is required?

Java 11 or higher.

### Does it work with JUnit 4?

Yes! While designed with JUnit 5 in mind, JSONAssertX works with any testing framework including JUnit 4, TestNG, or plain Java assertions.

---

## Installation & Setup

### How do I add JSONAssertX to my project?

**Maven:**
```xml
<dependency>
    <groupId>io.jsonassertx</groupId>
    <artifactId>jsonassertx-core</artifactId>
    <version>0.1.0</version>
    <scope>test</scope>
</dependency>
```

**Gradle:**
```gradle
testImplementation 'io.jsonassertx:jsonassertx-core:0.1.0'
```

### Do I need to add JSONPath separately?

No, JSONPath is included as a transitive dependency.

### Can I use it in production code?

While technically possible, JSONAssertX is designed for testing. We recommend keeping it in `test` scope.

---

## Usage Questions

### How do I test a JSON string?

```java
String json = "{\"name\": \"John\", \"age\": 30}";

JsonAssertX.assertThat(json)
    .path("$.name").asString().isEqualTo("John")
    .path("$.age").asNumber().isEqualTo(30);
```

### How do I test nested JSON?

Use JSONPath notation:

```java
String json = "{\"user\": {\"profile\": {\"email\": \"test@example.com\"}}}";

JsonAssertX.assertThat(json)
    .path("$.user.profile.email").asString()
    .contains("@example.com");
```

### How do I test arrays?

```java
String json = "{\"tags\": [\"java\", \"testing\", \"json\"]}";

JsonAssertX.assertThat(json)
    .path("$.tags").asArray()
    .hasSize(3)
    .contains("testing");
```

### How do I test specific array elements?

```java
// By index
.path("$.users[0].name").asString().isEqualTo("Alice")

// Using filters
.path("$.users[?(@.age > 18)]").asArray().hasSize(5)
```

### Can I use predicates to test array elements?

Yes:

```java
.path("$.scores").asArray()
    .allMatch(score -> ((Number) score).intValue() >= 80)
    .anyMatch(score -> ((Number) score).intValue() > 95);
```

### How do I check if a field exists?

```java
JsonAssertX.assertThat(json)
    .hasPath("$.user.email");
```

### How do I check if a field doesn't exist?

```java
JsonAssertX.assertThat(json)
    .doesNotHavePath("$.user.password");
```

### How do I test for null values?

```java
JsonAssertX.assertThat(json)
    .path("$.middleName").isNull();
```

### Can I chain multiple assertions?

Yes, that's the recommended approach:

```java
JsonAssertX.assertThat(json)
    .path("$.email").asString()
    .isNotEmpty()
    .contains("@")
    .endsWith(".com");
```

---

## JSONPath Questions

### What JSONPath syntax is supported?

All standard JSONPath expressions:
- `$.property` - Direct property access
- `$.parent.child` - Nested properties
- `$.array[0]` - Array index
- `$.array[*]` - All array elements
- `$..property` - Recursive descent
- `$.array[?(@.property > value)]` - Filtered arrays

See [JSONPath documentation](https://github.com/json-path/JsonPath) for complete syntax.

### How do I filter arrays?

```java
// Users older than 18
.path("$.users[?(@.age > 18)]")

// Products under $100
.path("$.products[?(@.price < 100)]")

// Active users
.path("$.users[?(@.active == true)]")
```

### Can I use wildcards?

Yes:

```java
// All user names
.path("$.users[*].name")

// All email fields at any depth
.path("$..email")
```

---

## Error Handling

### What happens when an assertion fails?

You get a clear `AssertionError` with details about what failed:

```
Expected path '$.user.age' to be greater than 30
Actual value: 25

JSON context:
{
  "user": {
    "name": "John",
    "age": 25  ⬅️
  }
}
```

### What if the JSONPath doesn't exist?

An `AssertionError` is thrown indicating the path was not found.

### What if the JSON is invalid?

A clear exception is thrown indicating the JSON parsing failed with details about the syntax error.

### Can I customize error messages?

Currently, error messages are automatically generated. Custom messages are planned for a future release.

---

## Performance

### Is JSONAssertX fast?

Yes. The library uses Jackson for JSON parsing, which is one of the fastest JSON parsers for Java. The fluent API adds minimal overhead.

### Should I be concerned about memory usage?

No. JSONAssertX processes JSON efficiently and doesn't hold large data structures in memory unnecessarily.

### Can I use it for large JSON files?

Yes, but be aware that the entire JSON is parsed into memory. For extremely large files (100MB+), consider streaming parsers instead.

---

## Integration

### Does it work with REST Assured?

Yes! You can combine them:

```java
String response = given()
    .when().get("/api/users/123")
    .then().extract().asString();

JsonAssertX.assertThat(response)
    .path("$.username").asString().isEqualTo("johndoe");
```

### Can I use it with Spring Boot?

Absolutely:

```java
@SpringBootTest
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testGetUser() throws Exception {
        String response = mockMvc.perform(get("/users/123"))
            .andReturn().getResponse().getContentAsString();
        
        JsonAssertX.assertThat(response)
            .path("$.username").asString().isEqualTo("johndoe");
    }
}
```

### Does it work with Jackson ObjectMapper?

Yes, you can serialize objects and test them:

```java
User user = new User("John", 30);
String json = objectMapper.writeValueAsString(user);

JsonAssertX.assertThat(json)
    .path("$.name").asString().isEqualTo("John");
```

---

## Comparison with Other Libraries

### How is this different from JSONAssert?

JSONAssert compares entire JSON documents. JSONAssertX focuses on fluent, specific assertions:

```java
// JSONAssert - String comparison
JSONAssert.assertEquals(expected, actual, false);

// JSONAssertX - Specific, readable assertions
JsonAssertX.assertThat(actual)
    .path("$.user.age").asNumber().isBetween(18, 65);
```

### How does it compare to AssertJ?

AssertJ has basic JSON support, but JSONAssertX is specialized for JSON with:
- Better JSONPath integration
- Type-specific assertions
- Clearer JSON-focused error messages

### Can I use it alongside other assertion libraries?

Yes! JSONAssertX complements libraries like AssertJ:

```java
// Use both in the same test
assertThat(list).hasSize(3);  // AssertJ
JsonAssertX.assertThat(json).path("$.items").asArray().hasSize(3);  // JSONAssertX
```

---

## Troubleshooting

### I'm getting "Path not found" errors

- Verify your JSONPath syntax is correct
- Use online JSONPath evaluators to test your path
- Check that your JSON is valid
- Ensure the path exists in your JSON structure

### The test passes but I expected it to fail

- Double-check your assertion logic
- Verify you're testing the right path
- Ensure you're using the correct comparison method

### Type conversion errors

Make sure you're using the right type conversion:
- `.asString()` for strings
- `.asNumber()` for numbers
- `.asBoolean()` for booleans
- `.asArray()` for arrays
- `.asObject()` for objects

---

## Contributing

### How can I contribute?

See our [Contributing Guide](../CONTRIBUTING.md) for:
- Reporting bugs
- Suggesting features
- Submitting pull requests
- Improving documentation

### Where can I report bugs?

Open an issue on [GitHub Issues](https://github.com/yourusername/jsonassertx/issues).

### Can I suggest new features?

Absolutely! Open a discussion on [GitHub Discussions](https://github.com/yourusername/jsonassertx/discussions).

---

## Roadmap

### What features are planned?

- **v0.2.0** - Snapshot testing (Jest-like)
- **v0.3.0** - JSON Schema validation
- **v0.4.0** - Type-safe code generation
- **v1.0.0** - Spring Boot integration

### When will version X.X be released?

Check the [GitHub milestones](https://github.com/yourusername/jsonassertx/milestones) for release plans.

---

## Still Have Questions?

- 💬 Ask in [GitHub Discussions](https://github.com/yourusername/jsonassertx/discussions)
- 📖 Read the [Getting Started Guide](getting-started.md)
- 📚 Check the [API Reference](api-reference.md)
- 🐛 Report issues on [GitHub Issues](https://github.com/yourusername/jsonassertx/issues)
