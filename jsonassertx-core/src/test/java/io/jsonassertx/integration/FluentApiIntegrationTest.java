package io.jsonassertx.integration;

import io.jsonassertx.JsonAssertX;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for fluent API chaining and complex validation flows.
 */
@DisplayName("Fluent API Integration Tests")
class FluentApiIntegrationTest {

    @Test
    @DisplayName("Complex chaining - Multiple assertions on same path")
    void testComplexChainingSamePath() {
        String json = "{\"email\": \"user@example.com\"}";

        JsonAssertX.assertThat(json)
            .path("$.email").asString()
            .startsWith("user");
        
        JsonAssertX.assertThat(json)
            .path("$.email").asString()
            .endsWith(".com");
        
        JsonAssertX.assertThat(json)
            .path("$.email").asString()
            .matches(".*@.*\\..*");
        
        JsonAssertX.assertThat(json)
            .path("$.email").asString()
            .hasLength(16);
    }

    @Test
    @DisplayName("Complex chaining - Multiple paths in sequence")
    void testComplexChainingMultiplePaths() {
        String json = "{"
            + "\"user\": {"
            + "  \"name\": \"John Doe\","
            + "  \"age\": 30,"
            + "  \"email\": \"john@example.com\","
            + "  \"active\": true,"
            + "  \"roles\": [\"admin\", \"user\"],"
            + "  \"profile\": {"
            + "    \"bio\": \"Software Developer\","
            + "    \"location\": \"San Francisco\""
            + "  }"
            + "}"
            + "}";

        JsonAssertX.assertThat(json)
            .path("$.user.name").asString().isEqualTo("John Doe")
            .path("$.user.age").asNumber().isBetween(18, 65)
            .path("$.user.email").asString().matches(".*@example\\.com")
            .path("$.user.active").asBoolean().isTrue()
            .path("$.user.roles").asArray().hasSize(2)
            .path("$.user.profile.bio").asString().matches(".*Developer.*")
            .path("$.user.profile.location").asString().isEqualTo("San Francisco");
    }

    @Test
    @DisplayName("Array element navigation and validation")
    void testArrayElementNavigation() {
        String json = "{"
            + "\"users\": ["
            + "  {\"id\": 1, \"name\": \"Alice\", \"age\": 25, \"active\": true},"
            + "  {\"id\": 2, \"name\": \"Bob\", \"age\": 30, \"active\": false},"
            + "  {\"id\": 3, \"name\": \"Charlie\", \"age\": 35, \"active\": true}"
            + "]"
            + "}";

        // Validate array
        JsonAssertX.assertThat(json)
            .path("$.users").asArray()
            .hasSize(3)
            .isNotEmpty();

        // Validate individual elements
        JsonAssertX.assertThat(json)
            .path("$.users[0].name").asString().isEqualTo("Alice")
            .path("$.users[0].age").asNumber().isEqualTo(25)
            .path("$.users[0].active").asBoolean().isTrue();

        JsonAssertX.assertThat(json)
            .path("$.users[1].name").asString().isEqualTo("Bob")
            .path("$.users[1].active").asBoolean().isFalse();

        JsonAssertX.assertThat(json)
            .path("$.users[2].name").asString().isEqualTo("Charlie")
            .path("$.users[2].age").asNumber().isGreaterThan(30);
    }

    @Test
    @DisplayName("Object structure validation with chaining")
    void testObjectStructureValidation() {
        String json = "{"
            + "\"config\": {"
            + "  \"database\": {\"host\": \"localhost\", \"port\": 5432, \"name\": \"mydb\"},"
            + "  \"cache\": {\"host\": \"localhost\", \"port\": 6379},"
            + "  \"api\": {\"version\": \"v1\", \"timeout\": 30}"
            + "}"
            + "}";

        JsonAssertX.assertThat(json)
            .path("$.config.database").asObject()
            .hasKeys("host", "port", "name")
            .hasKeyCount(3)
            .doesNotHaveKey("password");

        JsonAssertX.assertThat(json)
            .path("$.config.cache").asObject()
            .hasKeys("host", "port")
            .hasKeyCount(2);

        JsonAssertX.assertThat(json)
            .path("$.config.api").asObject()
            .hasKey("version")
            .hasKey("timeout");
    }

    @Test
    @DisplayName("Mixed type validation in single flow")
    void testMixedTypeValidation() {
        String json = "{"
            + "\"product\": {"
            + "  \"id\": 12345,"
            + "  \"name\": \"Laptop\","
            + "  \"price\": 999.99,"
            + "  \"inStock\": true,"
            + "  \"tags\": [\"electronics\", \"computers\"],"
            + "  \"specs\": {"
            + "    \"ram\": 16,"
            + "    \"storage\": 512"
            + "  }"
            + "}"
            + "}";

        JsonAssertX.assertThat(json)
            .path("$.product.id").asNumber().isPositive()
            .path("$.product.name").asString().isEqualTo("Laptop")
            .path("$.product.price").asNumber().isBetween(500, 2000)
            .path("$.product.inStock").asBoolean().isTrue()
            .path("$.product.tags").asArray().hasSize(2)
            .path("$.product.specs").asObject().hasKeys("ram", "storage")
            .path("$.product.specs.ram").asNumber().isGreaterThan(8);
    }

    @Test
    @DisplayName("Predicate-based array validation")
    void testPredicateArrayValidation() {
        String json = "{"
            + "\"employees\": ["
            + "  {\"name\": \"Alice\", \"salary\": 75000, \"department\": \"Engineering\"},"
            + "  {\"name\": \"Bob\", \"salary\": 85000, \"department\": \"Engineering\"},"
            + "  {\"name\": \"Charlie\", \"salary\": 65000, \"department\": \"Marketing\"}"
            + "]"
            + "}";

        JsonAssertX.assertThat(json)
            .path("$.employees").asArray()
            .hasSize(3)
            .allMatch(emp -> {
                if (emp instanceof com.fasterxml.jackson.databind.JsonNode) {
                    com.fasterxml.jackson.databind.JsonNode node = (com.fasterxml.jackson.databind.JsonNode) emp;
                    return node.get("salary").asInt() >= 60000;
                }
                return false;
            })
            .anyMatch(emp -> {
                if (emp instanceof com.fasterxml.jackson.databind.JsonNode) {
                    com.fasterxml.jackson.databind.JsonNode node = (com.fasterxml.jackson.databind.JsonNode) emp;
                    return node.get("department").asText().equals("Marketing");
                }
                return false;
            });
    }

    @Test
    @DisplayName("Recursive validation of nested structures")
    void testRecursiveNestedValidation() {
        String json = "{"
            + "\"company\": {"
            + "  \"name\": \"TechCorp\","
            + "  \"departments\": ["
            + "    {"
            + "      \"name\": \"Engineering\","
            + "      \"head\": {\"name\": \"Alice\", \"id\": 1},"
            + "      \"teams\": ["
            + "        {\"name\": \"Backend\", \"size\": 5},"
            + "        {\"name\": \"Frontend\", \"size\": 4}"
            + "      ]"
            + "    },"
            + "    {"
            + "      \"name\": \"Sales\","
            + "      \"head\": {\"name\": \"Bob\", \"id\": 2},"
            + "      \"teams\": ["
            + "        {\"name\": \"Enterprise\", \"size\": 8},"
            + "        {\"name\": \"SMB\", \"size\": 6}"
            + "      ]"
            + "    }"
            + "  ]"
            + "}"
            + "}";

        JsonAssertX.assertThat(json)
            .path("$.company.name").asString().isEqualTo("TechCorp")
            .path("$.company.departments").asArray().hasSize(2);

        // Engineering department
        JsonAssertX.assertThat(json)
            .path("$.company.departments[0].name").asString().isEqualTo("Engineering")
            .path("$.company.departments[0].head.name").asString().isEqualTo("Alice")
            .path("$.company.departments[0].teams").asArray().hasSize(2)
            .path("$.company.departments[0].teams[0].name").asString().isEqualTo("Backend")
            .path("$.company.departments[0].teams[0].size").asNumber().isEqualTo(5);

        // Sales department
        JsonAssertX.assertThat(json)
            .path("$.company.departments[1].name").asString().isEqualTo("Sales")
            .path("$.company.departments[1].head.name").asString().isEqualTo("Bob")
            .path("$.company.departments[1].teams").asArray().hasSize(2)
            .path("$.company.departments[1].teams[1].size").asNumber().isGreaterThan(5);
    }

    @Test
    @DisplayName("Full E2E validation - Order processing")
    void testFullE2EOrderProcessing() {
        String json = "{"
            + "\"order\": {"
            + "  \"orderId\": \"ORD-2024-12345\","
            + "  \"status\": \"processing\","
            + "  \"customer\": {"
            + "    \"customerId\": \"CUST-789\","
            + "    \"name\": \"Jane Smith\","
            + "    \"email\": \"jane@example.com\","
            + "    \"phone\": \"+1-555-0100\""
            + "  },"
            + "  \"items\": ["
            + "    {\"sku\": \"PROD-001\", \"name\": \"Widget\", \"quantity\": 2, \"price\": 29.99},"
            + "    {\"sku\": \"PROD-002\", \"name\": \"Gadget\", \"quantity\": 1, \"price\": 49.99}"
            + "  ],"
            + "  \"totals\": {"
            + "    \"subtotal\": 109.97,"
            + "    \"tax\": 8.80,"
            + "    \"shipping\": 10.00,"
            + "    \"total\": 128.77"
            + "  },"
            + "  \"shipping\": {"
            + "    \"method\": \"Standard\","
            + "    \"trackingNumber\": \"TRACK-123456\","
            + "    \"estimatedDays\": 5,"
            + "    \"address\": {"
            + "      \"street\": \"123 Main St\","
            + "      \"city\": \"Boston\","
            + "      \"state\": \"MA\","
            + "      \"zip\": \"02101\""
            + "    }"
            + "  },"
            + "  \"payment\": {"
            + "    \"method\": \"credit_card\","
            + "    \"last4\": \"4242\","
            + "    \"status\": \"authorized\","
            + "    \"amount\": 128.77"
            + "  },"
            + "  \"timestamps\": {"
            + "    \"created\": \"2024-01-15T10:00:00Z\","
            + "    \"updated\": \"2024-01-15T10:05:00Z\""
            + "  }"
            + "}"
            + "}";

        // Validate order header
        JsonAssertX.assertThat(json)
            .path("$.order.orderId").asString().startsWith("ORD-")
            .path("$.order.status").asString().isEqualTo("processing");

        // Validate customer
        JsonAssertX.assertThat(json)
            .path("$.order.customer").asObject().hasKeys("customerId", "name", "email", "phone")
            .path("$.order.customer.email").asString().matches(".*@.*\\..*")
            .path("$.order.customer.phone").asString().startsWith("+1");

        // Validate items
        JsonAssertX.assertThat(json)
            .path("$.order.items").asArray()
            .hasSize(2)
            .allMatch(item -> {
                if (item instanceof com.fasterxml.jackson.databind.JsonNode) {
                    com.fasterxml.jackson.databind.JsonNode node = (com.fasterxml.jackson.databind.JsonNode) item;
                    return node.has("sku") && 
                           node.has("name") && 
                           node.get("quantity").asInt() > 0 &&
                           node.get("price").asDouble() > 0;
                }
                return false;
            });

        // Validate totals
        JsonAssertX.assertThat(json)
            .path("$.order.totals.subtotal").asNumber().isPositive()
            .path("$.order.totals.tax").asNumber().isPositive()
            .path("$.order.totals.total").asNumber().isGreaterThan(100);

        // Validate shipping
        JsonAssertX.assertThat(json)
            .path("$.order.shipping.trackingNumber").asString().startsWith("TRACK-")
            .path("$.order.shipping.estimatedDays").asNumber().isBetween(1, 10)
            .path("$.order.shipping.address").asObject().hasKeys("street", "city", "state", "zip");

        // Validate payment
        JsonAssertX.assertThat(json)
            .path("$.order.payment.status").asString().isEqualTo("authorized")
            .path("$.order.payment.last4").asString().hasLength(4)
            .path("$.order.payment.amount").asNumber().isEqualTo(128.77);

        // Validate timestamps
        JsonAssertX.assertThat(json)
            .path("$.order.timestamps.created").asString().matches("\\d{4}-\\d{2}-\\d{2}T.*")
            .path("$.order.timestamps.updated").asString().matches("\\d{4}-\\d{2}-\\d{2}T.*");
    }
}
