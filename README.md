# JsonAssertX

**Fluent, intuitive JSON assertions for Java testing**

[![Java](https://img.shields.io/badge/Java-11%2B-blue)](https://openjdk.java.net/)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0)
[![Status](https://img.shields.io/badge/Status-Alpha-orange)]()

---

## Why JsonAssertX?

Testing JSON in Java is painful. Current solutions are either too verbose, hard to read, or give cryptic error messages.

**JsonAssertX makes JSON testing delightful** with:

✅ **Fluent, readable syntax** - Write assertions that read like English  
✅ **JSONPath support** - Navigate nested JSON effortlessly  
✅ **Smart array handling** - Test collections without loops  
✅ **Clear error messages** - Know exactly what failed and where  
✅ **Zero dependencies** - Just Jackson and JSONPath  
✅ **Type-aware** - Appropriate assertions for strings, numbers, arrays, objects

---

## Quick Start

### Installation

Maven:
```xml
<dependency>
    <groupId>io.jsonassertx</groupId>
    <artifactId>jsonassertx-core</artifactId>
    <version>0.1.0</version>
    <scope>test</scope>
</dependency>
```

Gradle:
```gradle
testImplementation 'io.jsonassertx:jsonassertx-core:0.1.0'
```

### Your First Assertion

```java
import static io.jsonassertx.JsonAssertX.assertThat;

@Test
void testUserProfile() {
    String response = """
        {
            "user": {
                "name": "John Doe",
                "age": 30,
                "email": "john@example.com",
                "active": true
            }
        }
        """;
    
    assertThat(response)
        .hasPath("$.user.name").isEqualTo("John Doe")
        .hasPath("$.user.age").isBetween(18, 100)
        .hasPath("$.user.email").contains("@example.com")
        .hasPath("$.user.active").isTrue();
}
```

That's it! Clean, readable, and works immediately.

---

## Examples

### Basic Assertions

```java
// String assertions
assertThat(json)
    .hasPath("$.username").isEqualTo("johndoe")
    .hasPath("$.bio").contains("developer")
    .hasPath("$.website").startsWith("https://")
    .hasPath("$.email").matches(".*@example\\.com");

// Number assertions
assertThat(json)
    .hasPath("$.price").isEqualTo(99.99)
    .hasPath("$.quantity").isGreaterThan(0)
    .hasPath("$.discount").isBetween(0, 100)
    .hasPath("$.balance").isPositive();

// Boolean assertions
assertThat(json)
    .hasPath("$.verified").isTrue()
    .hasPath("$.deleted").isFalse();

// Null checks
assertThat(json)
    .hasPath("$.middleName").isNull()
    .hasPath("$.lastName").isNotNull();
```

### Array Assertions

```java
String json = """
    {
        "users": ["Alice", "Bob", "Charlie"],
        "scores": [95, 87, 92]
    }
    """;

assertThat(json)
    .hasPath("$.users").isArray()
        .hasSize(3)
        .contains("Alice")
        .doesNotContain("David");

assertThat(json)
    .hasPath("$.scores").isArray()
        .isNotEmpty()
        .allMatch(score -> score.asInt() >= 0);
```

### Complex Nested JSON

```java
String json = """
    {
        "user": {
            "profile": {
                "name": "Jane Smith",
                "contact": {
                    "email": "jane@example.com",
                    "phone": "+1234567890"
                }
            },
            "roles": ["admin", "moderator"],
            "settings": {
                "notifications": true,
                "theme": "dark"
            }
        }
    }
    """;

assertThat(json)
    .hasPath("$.user.profile.name").isEqualTo("Jane Smith")
    .hasPath("$.user.profile.contact.email").contains("@")
    .hasPath("$.user.roles").isArray().hasSize(2)
    .hasPath("$.user.roles").contains("admin")
    .hasPath("$.user.settings.theme").isEqualTo("dark");
```

### Testing Real API Responses

```java
@Test
void testGitHubUserAPI() {
    String response = """
        {
            "login": "octocat",
            "id": 583231,
            "avatar_url": "https://avatars.githubusercontent.com/u/583231",
            "type": "User",
            "site_admin": false,
            "name": "The Octocat",
            "company": "@github",
            "followers": 8000,
            "following": 9,
            "created_at": "2011-01-25T18:44:36Z"
        }
        """;
    
    assertThat(response)
        .hasPath("$.login").isEqualTo("octocat")
        .hasPath("$.id").isPositive()
        .hasPath("$.type").isEqualTo("User")
        .hasPath("$.site_admin").isFalse()
        .hasPath("$.followers").isGreaterThan(1000)
        .hasPath("$.created_at").isNotEmpty();
}
```

### Object Structure Validation

```java
String json = """
    {
        "user": {
            "id": 123,
            "username": "johndoe",
            "email": "john@example.com"
        }
    }
    """;

assertThat(json)
    .hasPath("$.user").isObject()
        .hasKeys("id", "username", "email")
        .doesNotHaveKey("password");  // Ensure sensitive fields not exposed
```

### Array of Objects

```java
String json = """
    {
        "orders": [
            {"id": 1, "total": 100.50, "status": "completed"},
            {"id": 2, "total": 200.00, "status": "pending"},
            {"id": 3, "total": 50.25, "status": "completed"}
        ]
    }
    """;

assertThat(json)
    .hasPath("$.orders").isArray()
        .hasSize(3)
        .allMatch(order -> order.path("$.total").asDouble() > 0)
        .anyMatch(order -> order.path("$.status").equals("pending"));
```

---

## Comparison with Alternatives

### vs. JSONPath
```java
// JSONPath - Manual parsing, no fluent API
JsonPath jsonPath = JsonPath.from(response);
assertEquals("John", jsonPath.getString("user.name"));
assertEquals(30, jsonPath.getInt("user.age"));
assertTrue(jsonPath.getBoolean("user.active"));

// JsonAssertX - Fluent and readable
assertThat(response)
    .hasPath("$.user.name").isEqualTo("John")
    .hasPath("$.user.age").isEqualTo(30)
    .hasPath("$.user.active").isTrue();
```

### vs. AssertJ
```java
// AssertJ - String-based, limited JSON support
assertThatJson(response)
    .node("user.name").isEqualTo("John");

// JsonAssertX - Type-aware assertions
assertThat(response)
    .hasPath("$.user.age").isBetween(18, 100);  // ✅ Number-specific
```

### vs. Manual JSONObject
```java
// Manual - Verbose and brittle
JSONObject json = new JSONObject(response);
assertEquals("John", json.getJSONObject("user").getString("name"));

// JsonAssertX - Concise
assertThat(response)
    .hasPath("$.user.name").isEqualTo("John");
```

---

## Error Messages

When assertions fail, JsonAssertX shows exactly what went wrong:

```
❌ JSON Assertion Failed at path: $.user.age

Expected: 30
Actual:   25

JSON context:
{
  "user": {
    "name": "John",
    "age": 25  ⬅️ Here
  }
}
```

---

## Requirements

- **Java 11** or higher
- **Maven** or **Gradle**
- **JUnit 5** (recommended, but works with any testing framework)

---

## Documentation

- [Getting Started Guide](docs/getting-started.md)
- [API Reference](docs/api-reference.md)
- [Examples](jsonassertx-examples/)
- [FAQ](docs/faq.md)

---

## Roadmap

### Current: v0.1.0 (MVP)
- ✅ Fluent DSL for basic assertions
- ✅ Array and object assertions
- ✅ Clear error messages

### Coming Soon
- **v0.2.0** - Snapshot testing (Jest-like for Java)
- **v0.3.0** - JSON Schema validation
- **v0.4.0** - Type-safe assertions (code generation)
- **v1.0.0** - Spring Boot integration

---

## Contributing

We welcome contributions! See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

### Quick Contribution Ideas
- 📝 Improve documentation
- 🐛 Report bugs
- 💡 Suggest features
- ✅ Write tests
- 🎨 Enhance error messages

---

## License

Apache License 2.0 - See [LICENSE](LICENSE) for details.

---

## Support

- **Issues:** [GitHub Issues](https://github.com/yourusername/jsonassertx/issues)
- **Discussions:** [GitHub Discussions](https://github.com/yourusername/jsonassertx/discussions)
- **Email:** support@jsonassertx.io

---

## Acknowledgments

Inspired by:
- [AssertJ](https://assertj.github.io/doc/) - Fluent assertions
- [JSONPath](https://github.com/json-path/JsonPath) - JSON navigation
- [Jest](https://jestjs.io/) - Snapshot testing concept

Built with ❤️ for the Java testing community.

---

**⭐ Star this repo if you find it useful!**
