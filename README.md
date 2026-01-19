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
        .path("$.user.name").asString().isEqualTo("John Doe")
        .path("$.user.age").asNumber().isBetween(18, 100)
        .path("$.user.email").asString().contains("@example.com")
        .path("$.user.active").asBoolean().isTrue();
}
```

That's it! Clean, readable, and works immediately.

---

## Examples

### Basic Assertions

```java
// String assertions
assertThat(json)
    .path("$.username").asString().isEqualTo("johndoe")
    .path("$.bio").asString().contains("developer")
    .path("$.website").asString().startsWith("https://")
    .path("$.email").asString().matches(".*@example\\.com");

// Number assertions
assertThat(json)
    .path("$.price").asNumber().isEqualTo(99.99)
    .path("$.quantity").asNumber().isGreaterThan(0)
    .path("$.discount").asNumber().isBetween(0, 100)
    .path("$.balance").asNumber().isPositive();

// Boolean assertions
assertThat(json)
    .path("$.verified").asBoolean().isTrue()
    .path("$.deleted").asBoolean().isFalse();

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
    .path("$.users").asArray()
    .hasSize(3)
    .contains("Alice")
    .doesNotContain("David");

assertThat(json)
    .path("$.scores").asArray()
    .isNotEmpty()
    .allMatch(score -> ((Number) score).intValue() >= 0);
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
    .path("$.user.profile.name").asString().isEqualTo("Jane Smith")
    .path("$.user.profile.contact.email").asString().contains("@")
    .path("$.user.roles").asArray().hasSize(2)
    .path("$.user.roles").asArray().contains("admin")
    .path("$.user.settings.theme").asString().isEqualTo("dark");
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
        .path("$.login").asString().isEqualTo("octocat")
        .path("$.id").asNumber().isPositive()
        .path("$.type").asString().isEqualTo("User")
        .path("$.site_admin").asBoolean().isFalse()
        .path("$.followers").asNumber().isGreaterThan(1000)
        .path("$.created_at").asString().isNotEmpty();
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
    .path("$.user").asObject()
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
    .path("$.orders").asArray()
    .hasSize(3)
    .allMatch(order -> {
        if (order instanceof com.fasterxml.jackson.databind.JsonNode) {
            return ((com.fasterxml.jackson.databind.JsonNode) order).get("total").asDouble() > 0;
        }
        return false;
    });
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
    .path("$.user.name").asString().isEqualTo("John")
    .path("$.user.age").asNumber().isEqualTo(30)
    .path("$.user.active").asBoolean().isTrue();
```

### vs. AssertJ
```java
// AssertJ - String-based, limited JSON support
assertThatJson(response)
    .node("user.name").isEqualTo("John");

// JsonAssertX - Type-aware assertions
assertThat(response)
    .path("$.user.age").asNumber().isBetween(18, 100);  // ✅ Number-specific
```

### vs. Manual JSONObject
```java
// Manual - Verbose and brittle
JSONObject json = new JSONObject(response);
assertEquals("John", json.getJSONObject("user").getString("name"));

// JsonAssertX - Concise
assertThat(response)
    .path("$.user.name").asString().isEqualTo("John");
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
