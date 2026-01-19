# Getting Started with JSONAssertX

Complete guide to get you up and running with JSONAssertX in minutes.

---

## Installation

### Maven

Add to your `pom.xml`:

```xml
<dependency>
    <groupId>io.jsonassertx</groupId>
    <artifactId>jsonassertx-core</artifactId>
    <version>0.1.0</version>
    <scope>test</scope>
</dependency>
```

### Gradle

Add to your `build.gradle`:

```gradle
testImplementation 'io.jsonassertx:jsonassertx-core:0.1.0'
```

---

## Your First Test

### Step 1: Import the Library

```java
import io.jsonassertx.JsonAssertX;
```

### Step 2: Write Your First Assertion

```java
import io.jsonassertx.JsonAssertX;
import org.junit.jupiter.api.Test;

class MyFirstJsonTest {
    
    @Test
    void testSimpleJson() {
        String json = "{\"name\": \"John\", \"age\": 30}";
        
        JsonAssertX.assertThat(json)
            .path("$.name").asString()
            .isEqualTo("John");
    }
}
```

### Step 3: Run Your Test

```bash
mvn test
```

That's it! You've written your first JSON assertion.

---

## Core Concepts

### 1. Entry Point

Every assertion starts with `JsonAssertX.assertThat()`:

```java
JsonAssertX.assertThat(jsonString)
    // ... assertions
```

### 2. Path Navigation

Use JSONPath to navigate to specific parts of your JSON:

```java
JsonAssertX.assertThat(json)
    .path("$.user.email")  // Navigate to user.email
    .asString()             // Treat as string
    .contains("@");         // Assert contains @
```

### 3. Type-Specific Assertions

Cast to the appropriate type for specialized assertions:

```java
// String assertions
.path("$.name").asString().isEqualTo("John")

// Number assertions
.path("$.age").asNumber().isGreaterThan(18)

// Boolean assertions
.path("$.active").asBoolean().isTrue()

// Array assertions
.path("$.tags").asArray().hasSize(3)

// Object assertions
.path("$.user").asObject().hasKey("email")
```

### 4. Method Chaining

Chain multiple assertions together:

```java
JsonAssertX.assertThat(json)
    .path("$.email").asString()
    .contains("@")
    .endsWith(".com")
    .hasLength(20);
```

---

## Common Use Cases

### Testing REST API Responses

```java
@Test
void testUserApiResponse() {
    String response = callApi("/api/users/123");
    
    JsonAssertX.assertThat(response)
        .path("$.id").asNumber().isEqualTo(123)
        .path("$.username").asString().isNotEmpty()
        .path("$.email").asString().matches(".*@.*\\..*")
        .path("$.active").asBoolean().isTrue();
}
```

### Validating Nested Structures

```java
@Test
void testNestedJson() {
    String json = """
        {
            "order": {
                "customer": {
                    "name": "Alice",
                    "email": "alice@example.com"
                },
                "items": [
                    {"id": 1, "price": 29.99},
                    {"id": 2, "price": 49.99}
                ]
            }
        }
        """;
    
    JsonAssertX.assertThat(json)
        .path("$.order.customer.name").asString().isEqualTo("Alice")
        .path("$.order.items").asArray().hasSize(2)
        .path("$.order.items[0].price").asNumber().isPositive();
}
```

### Testing Array Contents

```java
@Test
void testArrayElements() {
    String json = "{\"scores\": [85, 90, 95, 88]}";
    
    JsonAssertX.assertThat(json)
        .path("$.scores").asArray()
        .hasSize(4)
        .contains(90)
        .allMatch(score -> ((Number) score).intValue() >= 80);
}
```

### Checking Object Structure

```java
@Test
void testObjectStructure() {
    String json = "{\"user\": {\"name\": \"Bob\", \"age\": 25, \"email\": \"bob@test.com\"}}";
    
    JsonAssertX.assertThat(json)
        .path("$.user").asObject()
        .hasKeys("name", "age", "email")
        .doesNotHaveKey("password");
}
```

---

## JSONPath Quick Reference

JSONAssertX uses [JSONPath](https://github.com/json-path/JsonPath) for navigation:

| Pattern | Description | Example |
|---------|-------------|---------|
| `$` | Root element | `$.user` |
| `.` | Child element | `$.user.name` |
| `[]` | Array access | `$.items[0]` |
| `[*]` | All array elements | `$.items[*].price` |
| `[?()]` | Filter | `$.users[?(@.age > 18)]` |
| `..` | Recursive descent | `$..email` |

### Common JSONPath Examples

```java
// Direct property
.path("$.username")

// Nested property
.path("$.user.profile.name")

// Array element by index
.path("$.items[0].name")

// All array elements
.path("$.items[*].price")

// Filter array elements
.path("$.users[?(@.age >= 18)].name")

// All email fields anywhere
.path("$..email")
```

---

## Error Messages

When assertions fail, you get clear error messages:

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

---

## Best Practices

### 1. Use Descriptive Paths

```java
// Good ✅
.path("$.order.customer.email")

// Less clear ❌
.path("$['order']['customer']['email']")
```

### 2. Chain Related Assertions

```java
// Good ✅
JsonAssertX.assertThat(json)
    .path("$.email").asString()
    .contains("@")
    .endsWith(".com");

// Less efficient ❌
JsonAssertX.assertThat(json).path("$.email").asString().contains("@");
JsonAssertX.assertThat(json).path("$.email").asString().endsWith(".com");
```

### 3. Use Type-Specific Assertions

```java
// Good ✅
.path("$.age").asNumber().isBetween(18, 65)

// Less type-safe ❌
.path("$.age").hasValue("$.age", 30)
```

### 4. Test Multiple Properties

```java
JsonAssertX.assertThat(json)
    .path("$.user.name").asString().isNotEmpty()
    .path("$.user.age").asNumber().isPositive()
    .path("$.user.active").asBoolean().isTrue();
```

---

## Next Steps

- **[API Reference](api-reference.md)** - Complete method documentation
- **[FAQ](faq.md)** - Common questions and answers
- **[Examples](../jsonassertx-examples/)** - Runnable code examples
- **[Contributing](../CONTRIBUTING.md)** - Help improve the library

---

## Need Help?

- 📖 Check the [FAQ](faq.md)
- 💬 Ask in [GitHub Discussions](https://github.com/yourusername/jsonassertx/discussions)
- 🐛 Report issues on [GitHub Issues](https://github.com/yourusername/jsonassertx/issues)
