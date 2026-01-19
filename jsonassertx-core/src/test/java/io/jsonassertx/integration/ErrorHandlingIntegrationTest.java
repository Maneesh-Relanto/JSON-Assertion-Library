package io.jsonassertx.integration;

import io.jsonassertx.JsonAssertX;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration tests for error handling and edge cases.
 */
@DisplayName("Error Handling Integration Tests")
class ErrorHandlingIntegrationTest {

    @Test
    @DisplayName("Invalid JSON - Should throw clear error")
    void testInvalidJsonHandling() {
        String invalidJson = "{invalid json";

        assertThrows(Exception.class, () -> {
            JsonAssertX.assertThat(invalidJson)
                .path("$.name").asString();
        });
    }

    @Test
    @DisplayName("Non-existent path - Should throw AssertionError")
    void testNonExistentPath() {
        String json = "{\"name\": \"John\"}";

        assertThrows(AssertionError.class, () -> {
            JsonAssertX.assertThat(json)
                .path("$.nonexistent").asString();
        });
    }

    @Test
    @DisplayName("Type mismatch - String expected but number found")
    void testTypeMismatchStringToNumber() {
        String json = "{\"age\": 30}";

        assertThrows(AssertionError.class, () -> {
            JsonAssertX.assertThat(json)
                .path("$.age").asString()
                .isEqualTo("30");
        });
    }

    @Test
    @DisplayName("Type mismatch - Number expected but string found")
    void testTypeMismatchNumberToString() {
        String json = "{\"name\": \"John\"}";

        assertThrows(AssertionError.class, () -> {
            JsonAssertX.assertThat(json)
                .path("$.name").asNumber()
                .isEqualTo(123);
        });
    }

    @Test
    @DisplayName("Array expected but object found")
    void testArrayTypeMismatch() {
        String json = "{\"user\": {\"name\": \"John\"}}";

        assertThrows(AssertionError.class, () -> {
            JsonAssertX.assertThat(json)
                .path("$.user").asArray()
                .hasSize(1);
        });
    }

    @Test
    @DisplayName("Object expected but array found")
    void testObjectTypeMismatch() {
        String json = "{\"items\": [1, 2, 3]}";

        assertThrows(AssertionError.class, () -> {
            JsonAssertX.assertThat(json)
                .path("$.items").asObject()
                .hasKey("name");
        });
    }

    @Test
    @DisplayName("Empty JSON object")
    void testEmptyJsonObject() {
        String json = "{}";

        JsonAssertX.assertThat(json)
            .isNotNull()
            .isEmpty();
    }

    @Test
    @DisplayName("Empty JSON array")
    void testEmptyJsonArray() {
        String json = "{\"items\": []}";

        JsonAssertX.assertThat(json)
            .path("$.items").asArray()
            .isEmpty();
    }

    @Test
    @DisplayName("Null values in JSON")
    void testNullValues() {
        String json = "{\"name\": \"John\", \"middleName\": null, \"age\": 30}";

        JsonAssertX.assertThat(json)
            .path("$.name").asString().isEqualTo("John")
            .path("$.middleName").isNull()
            .path("$.age").asNumber().isEqualTo(30);
    }

    @Test
    @DisplayName("Deep nesting - 10 levels")
    void testDeeplyNestedJson() {
        String json = "{"
            + "\"level1\": {"
            + "  \"level2\": {"
            + "    \"level3\": {"
            + "      \"level4\": {"
            + "        \"level5\": {"
            + "          \"level6\": {"
            + "            \"level7\": {"
            + "              \"level8\": {"
            + "                \"level9\": {"
            + "                  \"level10\": {\"value\": \"deep\"}"
            + "                }"
            + "              }"
            + "            }"
            + "          }"
            + "        }"
            + "      }"
            + "    }"
            + "  }"
            + "}"
            + "}";

        JsonAssertX.assertThat(json)
            .path("$.level1.level2.level3.level4.level5.level6.level7.level8.level9.level10.value")
            .asString()
            .isEqualTo("deep");
    }

    @Test
    @DisplayName("Unicode and special characters")
    void testUnicodeAndSpecialCharacters() {
        String json = "{"
            + "\"emoji\": \"😀🎉\","
            + "\"chinese\": \"你好\","
            + "\"arabic\": \"مرحبا\","
            + "\"special\": \"Line1\\nLine2\\tTabbed\","
            + "\"quote\": \"He said \\\"Hello\\\"\""
            + "}";

        JsonAssertX.assertThat(json)
            .path("$.emoji").asString().isNotEmpty()
            .path("$.chinese").asString().isEqualTo("你好")
            .path("$.arabic").asString().isEqualTo("مرحبا")
            .path("$.special").asString().contains("Line1")
            .path("$.quote").asString().contains("Hello");
    }

    @Test
    @DisplayName("Large numbers and precision")
    void testLargeNumbersAndPrecision() {
        String json = "{"
            + "\"largeInt\": 9007199254740991,"
            + "\"largeNegative\": -9007199254740991,"
            + "\"decimal\": 123.456789,"
            + "\"scientific\": 1.23e10,"
            + "\"verySmall\": 0.000000001"
            + "}";

        JsonAssertX.assertThat(json)
            .path("$.largeInt").asNumber().isGreaterThan(1000000000)
            .path("$.largeNegative").asNumber().isNegative()
            .path("$.decimal").asNumber().isBetween(123, 124)
            .path("$.scientific").asNumber().isGreaterThan(1000000000)
            .path("$.verySmall").asNumber().isPositive();
    }

    @Test
    @DisplayName("Mixed array types")
    void testMixedArrayTypes() {
        String json = "{"
            + "\"mixed\": [\"string\", 123, true, null, {\"key\": \"value\"}, [1, 2, 3]]"
            + "}";

        JsonAssertX.assertThat(json)
            .path("$.mixed").asArray()
            .hasSize(6)
            .contains("string")
            .contains(123);
    }

    @Test
    @DisplayName("Array out of bounds access")
    void testArrayOutOfBounds() {
        String json = "{\"items\": [1, 2, 3]}";

        assertThrows(AssertionError.class, () -> {
            JsonAssertX.assertThat(json)
                .path("$.items").asArray()
                .element(10);
        });
    }

    @Test
    @DisplayName("Negative array index")
    void testNegativeArrayIndex() {
        String json = "{\"items\": [1, 2, 3]}";

        assertThrows(AssertionError.class, () -> {
            JsonAssertX.assertThat(json)
                .path("$.items").asArray()
                .element(-1);
        });
    }

    @Test
    @DisplayName("String length edge cases")
    void testStringLengthEdgeCases() {
        String json = "{"
            + "\"empty\": \"\","
            + "\"oneChar\": \"a\","
            + "\"long\": \"" + "a".repeat(1000) + "\""
            + "}";

        JsonAssertX.assertThat(json)
            .path("$.empty").asString().hasLength(0)
            .path("$.oneChar").asString().hasLength(1)
            .path("$.long").asString().hasLength(1000);
    }

    @Test
    @DisplayName("Boolean edge cases")
    void testBooleanEdgeCases() {
        String json = "{"
            + "\"trueValue\": true,"
            + "\"falseValue\": false,"
            + "\"notBoolean\": \"true\""
            + "}";

        JsonAssertX.assertThat(json)
            .path("$.trueValue").asBoolean().isTrue()
            .path("$.falseValue").asBoolean().isFalse();

        // String "true" should not be treated as boolean
        assertThrows(AssertionError.class, () -> {
            JsonAssertX.assertThat(json)
                .path("$.notBoolean").asBoolean()
                .isTrue();
        });
    }

    @Test
    @DisplayName("Complex JSONPath expressions")
    void testComplexJsonPathExpressions() {
        String json = "{"
            + "\"store\": {"
            + "  \"books\": ["
            + "    {\"title\": \"Book1\", \"price\": 10.99, \"category\": \"fiction\"},"
            + "    {\"title\": \"Book2\", \"price\": 15.99, \"category\": \"fiction\"},"
            + "    {\"title\": \"Book3\", \"price\": 8.99, \"category\": \"nonfiction\"}"
            + "  ]"
            + "}"
            + "}";

        // Filter books by category
        JsonAssertX.assertThat(json)
            .path("$.store.books[?(@.category == 'fiction')]").asArray()
            .hasSize(2);

        // Filter books by price
        JsonAssertX.assertThat(json)
            .path("$.store.books[?(@.price < 12)]").asArray()
            .hasSize(2);
    }

    @Test
    @DisplayName("Whitespace handling in JSON")
    void testWhitespaceHandling() {
        String json = "  {  \"name\"  :  \"John\"  ,  \"age\"  :  30  }  ";

        JsonAssertX.assertThat(json)
            .path("$.name").asString().isEqualTo("John")
            .path("$.age").asNumber().isEqualTo(30);
    }
}
