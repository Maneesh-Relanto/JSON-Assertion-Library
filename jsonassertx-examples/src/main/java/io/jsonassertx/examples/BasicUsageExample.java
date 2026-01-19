package io.jsonassertx.examples;

import io.jsonassertx.JsonAssertX;

/**
 * Basic usage examples demonstrating core JSON assertion capabilities.
 */
public class BasicUsageExample {

    public static void main(String[] args) {
        basicStringAssertions();
        basicNumberAssertions();
        basicBooleanAssertions();
        basicArrayAssertions();
        basicObjectAssertions();
        jsonPathNavigation();
    }

    /**
     * Demonstrates string assertions on JSON properties.
     */
    private static void basicStringAssertions() {
        System.out.println("\n=== String Assertions ===");
        
        String json = "{\"name\": \"John Doe\", \"email\": \"john@example.com\", \"city\": \"New York\"}";
        
        // Assert string equals
        JsonAssertX.assertThat(json)
            .path("$.name").asString()
            .isEqualTo("John Doe");
        System.out.println("✓ Name equals 'John Doe'");
        
        // Assert string contains
        JsonAssertX.assertThat(json)
            .path("$.email").asString()
            .contains("@example.com");
        System.out.println("✓ Email contains '@example.com'");
        
        // Assert string starts with
        JsonAssertX.assertThat(json)
            .path("$.city").asString()
            .startsWith("New");
        System.out.println("✓ City starts with 'New'");
        
        // Assert string matches pattern
        JsonAssertX.assertThat(json)
            .path("$.email").asString()
            .matches("^[a-z]+@[a-z]+\\.[a-z]+$");
        System.out.println("✓ Email matches email pattern");
    }

    /**
     * Demonstrates number assertions with comparisons and ranges.
     */
    private static void basicNumberAssertions() {
        System.out.println("\n=== Number Assertions ===");
        
        String json = "{\"age\": 30, \"temperature\": -5.5, \"score\": 95.5, \"count\": 0}";
        
        // Assert number equals
        JsonAssertX.assertThat(json)
            .path("$.age").asNumber()
            .isEqualTo(30);
        System.out.println("✓ Age equals 30");
        
        // Assert number greater than
        JsonAssertX.assertThat(json)
            .path("$.score").asNumber()
            .isGreaterThan(90);
        System.out.println("✓ Score greater than 90");
        
        // Assert number in range
        JsonAssertX.assertThat(json)
            .path("$.age").asNumber()
            .isBetween(18, 65);
        System.out.println("✓ Age between 18 and 65");
        
        // Assert number is negative
        JsonAssertX.assertThat(json)
            .path("$.temperature").asNumber()
            .isNegative();
        System.out.println("✓ Temperature is negative");
        
        // Assert number is zero
        JsonAssertX.assertThat(json)
            .path("$.count").asNumber()
            .isZero();
        System.out.println("✓ Count is zero");
    }

    /**
     * Demonstrates boolean assertions.
     */
    private static void basicBooleanAssertions() {
        System.out.println("\n=== Boolean Assertions ===");
        
        String json = "{\"active\": true, \"deleted\": false, \"verified\": true}";
        
        // Assert boolean is true
        JsonAssertX.assertThat(json)
            .path("$.active").asBoolean()
            .isTrue();
        System.out.println("✓ Active is true");
        
        // Assert boolean is false
        JsonAssertX.assertThat(json)
            .path("$.deleted").asBoolean()
            .isFalse();
        System.out.println("✓ Deleted is false");
        
        // Assert boolean is true (verified)
        JsonAssertX.assertThat(json)
            .path("$.verified").asBoolean()
            .isTrue();
        System.out.println("✓ Verified is true");
    }

    /**
     * Demonstrates array assertions with predicates.
     */
    private static void basicArrayAssertions() {
        System.out.println("\n=== Array Assertions ===");
        
        String json = "{\"tags\": [\"java\", \"testing\", \"json\"], \"scores\": [85, 90, 95], \"items\": []}";
        
        // Assert array has size
        JsonAssertX.assertThat(json)
            .path("$.tags").asArray()
            .hasSize(3);
        System.out.println("✓ Tags has size 3");
        
        // Assert array contains element
        JsonAssertX.assertThat(json)
            .path("$.tags").asArray()
            .contains("testing");
        System.out.println("✓ Tags contains 'testing'");
        
        // Assert array does not contain element
        JsonAssertX.assertThat(json)
            .path("$.tags").asArray()
            .doesNotContain("python");
        System.out.println("✓ Tags does not contain 'python'");
        
        // Assert all elements match predicate
        JsonAssertX.assertThat(json)
            .path("$.scores").asArray()
            .allMatch(score -> (int) score >= 80);
        System.out.println("✓ All scores >= 80");
        
        // Assert any element matches predicate
        JsonAssertX.assertThat(json)
            .path("$.scores").asArray()
            .anyMatch(score -> (int) score > 90);
        System.out.println("✓ Any score > 90");
        
        // Assert array is empty
        JsonAssertX.assertThat(json)
            .path("$.items").asArray()
            .isEmpty();
        System.out.println("✓ Items is empty");
    }

    /**
     * Demonstrates object structure assertions.
     */
    private static void basicObjectAssertions() {
        System.out.println("\n=== Object Assertions ===");
        
        String json = "{\"user\": {\"name\": \"John\", \"age\": 30, \"email\": \"john@example.com\"}}";
        
        // Assert object has key
        JsonAssertX.assertThat(json)
            .path("$.user").isObject()
            .hasKey("name");
        System.out.println("✓ User has key 'name'");
        
        // Assert object has multiple keys
        JsonAssertX.assertThat(json)
            .path("$.user").isObject()
            .hasKeys("name", "age", "email");
        System.out.println("✓ User has keys 'name', 'age', 'email'");
        
        // Assert object does not have key
        JsonAssertX.assertThat(json)
            .path("$.user").isObject()
            .doesNotHaveKey("password");
        System.out.println("✓ User does not have key 'password'");
        
        // Assert object key count
        JsonAssertX.assertThat(json)
            .path("$.user").isObject()
            .hasKeyCount(3);
        System.out.println("✓ User has 3 keys");
    }

    /**
     * Demonstrates JSONPath navigation for nested structures.
     */
    private static void jsonPathNavigation() {
        System.out.println("\n=== JSONPath Navigation ===");
        
        String json = "{"
            + "\"company\": {"
            + "  \"name\": \"TechCorp\","
            + "  \"employees\": ["
            + "    {\"name\": \"Alice\", \"role\": \"Engineer\", \"salary\": 120000},"
            + "    {\"name\": \"Bob\", \"role\": \"Manager\", \"salary\": 150000},"
            + "    {\"name\": \"Charlie\", \"role\": \"Engineer\", \"salary\": 110000}"
            + "  ]"
            + "}}";
        
        // Navigate to nested property
        JsonAssertX.assertThat(json)
            .path("$.company.name").asString()
            .isEqualTo("TechCorp");
        System.out.println("✓ Company name is 'TechCorp'");
        
        // Navigate to array element
        JsonAssertX.assertThat(json)
            .path("$.company.employees[0].name").asString()
            .isEqualTo("Alice");
        System.out.println("✓ First employee is 'Alice'");
        
        // Navigate to array with predicate
        JsonAssertX.assertThat(json)
            .path("$.company.employees[?(@.role == 'Manager')].name")
            .asArray()
            .contains("Bob");
        System.out.println("✓ Manager is 'Bob'");
        
        // Navigate to array size
        JsonAssertX.assertThat(json)
            .path("$.company.employees").asArray()
            .hasSize(3);
        System.out.println("✓ Company has 3 employees");
        
        // Check all engineers have salary >= 100000
        JsonAssertX.assertThat(json)
            .path("$.company.employees[?(@.role == 'Engineer')].salary").asArray()
            .allMatch(salary -> (int) salary >= 100000);
        System.out.println("✓ All engineers have salary >= 100000");
        
        System.out.println("\n=== All Examples Passed! ===\n");
    }
}
