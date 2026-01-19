package io.jsonassertx.integration;

import io.jsonassertx.JsonAssertX;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Integration tests simulating real-world API response validation scenarios.
 */
@DisplayName("Real-World API Integration Tests")
class RealWorldApiIntegrationTest {

    @Test
    @DisplayName("GitHub User API - Complete validation")
    void testGitHubUserApiResponse() {
        String githubUser = "{"
            + "\"login\": \"octocat\","
            + "\"id\": 583231,"
            + "\"node_id\": \"MDQ6VXNlcjU4MzIzMQ==\","
            + "\"avatar_url\": \"https://avatars.githubusercontent.com/u/583231?v=4\","
            + "\"gravatar_id\": \"\","
            + "\"url\": \"https://api.github.com/users/octocat\","
            + "\"html_url\": \"https://github.com/octocat\","
            + "\"type\": \"User\","
            + "\"site_admin\": false,"
            + "\"name\": \"The Octocat\","
            + "\"company\": \"@github\","
            + "\"blog\": \"https://github.blog\","
            + "\"location\": \"San Francisco\","
            + "\"email\": null,"
            + "\"hireable\": null,"
            + "\"bio\": \"GitHub mascot\","
            + "\"twitter_username\": null,"
            + "\"public_repos\": 8,"
            + "\"public_gists\": 8,"
            + "\"followers\": 9999,"
            + "\"following\": 9,"
            + "\"created_at\": \"2011-01-25T18:44:36Z\","
            + "\"updated_at\": \"2024-01-15T12:00:00Z\""
            + "}";

        JsonAssertX.assertThat(githubUser)
            .path("$.login").asString().isEqualTo("octocat")
            .path("$.id").asNumber().isPositive()
            .path("$.type").asString().isEqualTo("User")
            .path("$.site_admin").asBoolean().isFalse()
            .path("$.avatar_url").asString().startsWith("https://")
            .path("$.avatar_url").asString().matches(".*githubusercontent\\.com.*")
            .path("$.url").asString().matches("https://api\\.github\\.com/users/.*")
            .path("$.public_repos").asNumber().isGreaterThanOrEqualTo(0)
            .path("$.followers").asNumber().isGreaterThan(1000)
            .path("$.created_at").asString().matches("\\d{4}-\\d{2}-\\d{2}T.*")
            .path("$.email").isNull()
            .doesNotHavePath("$.private_data");
    }

    @Test
    @DisplayName("E-Commerce Order API - Complex nested validation")
    void testECommerceOrderApiResponse() {
        String order = "{"
            + "\"orderId\": \"ORD-2024-001234\","
            + "\"status\": \"confirmed\","
            + "\"orderDate\": \"2024-01-15T10:30:00Z\","
            + "\"customer\": {"
            + "  \"customerId\": \"CUST-12345\","
            + "  \"name\": \"Jane Doe\","
            + "  \"email\": \"jane.doe@example.com\","
            + "  \"phone\": \"+1-555-0123\","
            + "  \"verified\": true"
            + "},"
            + "\"items\": ["
            + "  {"
            + "    \"itemId\": \"ITEM-001\","
            + "    \"name\": \"Laptop\","
            + "    \"category\": \"Electronics\","
            + "    \"quantity\": 1,"
            + "    \"unitPrice\": 999.99,"
            + "    \"discount\": 50.00,"
            + "    \"total\": 949.99"
            + "  },"
            + "  {"
            + "    \"itemId\": \"ITEM-002\","
            + "    \"name\": \"Wireless Mouse\","
            + "    \"category\": \"Accessories\","
            + "    \"quantity\": 2,"
            + "    \"unitPrice\": 29.99,"
            + "    \"discount\": 0,"
            + "    \"total\": 59.98"
            + "  }"
            + "],"
            + "\"pricing\": {"
            + "  \"subtotal\": 1009.97,"
            + "  \"tax\": 80.80,"
            + "  \"shipping\": 15.00,"
            + "  \"discount\": 50.00,"
            + "  \"total\": 1055.77"
            + "},"
            + "\"shipping\": {"
            + "  \"method\": \"Standard\","
            + "  \"address\": {"
            + "    \"street\": \"123 Main St\","
            + "    \"city\": \"Boston\","
            + "    \"state\": \"MA\","
            + "    \"zipCode\": \"02101\","
            + "    \"country\": \"USA\""
            + "  },"
            + "  \"estimatedDelivery\": \"2024-01-20\""
            + "},"
            + "\"payment\": {"
            + "  \"method\": \"credit_card\","
            + "  \"last4\": \"4242\","
            + "  \"status\": \"completed\""
            + "}"
            + "}";

        // Validate order header
        JsonAssertX.assertThat(order)
            .path("$.orderId").asString().startsWith("ORD-")
            .path("$.status").asString().isEqualTo("confirmed")
            .path("$.orderDate").asString().matches("\\d{4}-\\d{2}-\\d{2}T.*");

        // Validate customer
        JsonAssertX.assertThat(order)
            .path("$.customer").asObject().hasKeys("customerId", "name", "email", "phone")
            .path("$.customer.email").asString().matches(".*@.*\\..*")
            .path("$.customer.verified").asBoolean().isTrue();

        // Validate items array
        JsonAssertX.assertThat(order)
            .path("$.items").asArray()
            .hasSize(2)
            .allMatch(item -> {
                if (item instanceof com.fasterxml.jackson.databind.JsonNode) {
                    com.fasterxml.jackson.databind.JsonNode node = (com.fasterxml.jackson.databind.JsonNode) item;
                    return node.has("itemId") && node.has("name") && node.has("total");
                }
                return false;
            });

        // Validate individual items
        JsonAssertX.assertThat(order)
            .path("$.items[0].name").asString().isEqualTo("Laptop")
            .path("$.items[0].total").asNumber().isPositive()
            .path("$.items[1].name").asString().isEqualTo("Wireless Mouse")
            .path("$.items[1].quantity").asNumber().isEqualTo(2);

        // Validate pricing
        JsonAssertX.assertThat(order)
            .path("$.pricing.total").asNumber().isGreaterThan(1000)
            .path("$.pricing.tax").asNumber().isPositive()
            .path("$.pricing.discount").asNumber().isGreaterThanOrEqualTo(0);

        // Validate shipping
        JsonAssertX.assertThat(order)
            .path("$.shipping.address").asObject().hasKeys("street", "city", "state", "zipCode", "country")
            .path("$.shipping.address.country").asString().isEqualTo("USA");

        // Validate payment
        JsonAssertX.assertThat(order)
            .path("$.payment.status").asString().isEqualTo("completed")
            .path("$.payment.last4").asString().hasLength(4);
    }

    @Test
    @DisplayName("Weather API - Array and nested object validation")
    void testWeatherApiResponse() {
        String weather = "{"
            + "\"location\": {"
            + "  \"name\": \"San Francisco\","
            + "  \"region\": \"California\","
            + "  \"country\": \"USA\","
            + "  \"lat\": 37.77,"
            + "  \"lon\": -122.42,"
            + "  \"tz_id\": \"America/Los_Angeles\","
            + "  \"localtime\": \"2024-01-15 10:30\""
            + "},"
            + "\"current\": {"
            + "  \"last_updated\": \"2024-01-15 10:00\","
            + "  \"temp_c\": 18.5,"
            + "  \"temp_f\": 65.3,"
            + "  \"is_day\": 1,"
            + "  \"condition\": {"
            + "    \"text\": \"Partly cloudy\","
            + "    \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/116.png\","
            + "    \"code\": 1003"
            + "  },"
            + "  \"wind_mph\": 6.9,"
            + "  \"wind_kph\": 11.2,"
            + "  \"wind_degree\": 230,"
            + "  \"wind_dir\": \"SW\","
            + "  \"pressure_mb\": 1013.0,"
            + "  \"humidity\": 72,"
            + "  \"cloud\": 50,"
            + "  \"feelslike_c\": 17.0,"
            + "  \"feelslike_f\": 62.6,"
            + "  \"uv\": 5.0"
            + "},"
            + "\"forecast\": {"
            + "  \"forecastday\": ["
            + "    {"
            + "      \"date\": \"2024-01-15\","
            + "      \"day\": {"
            + "        \"maxtemp_c\": 20.0,"
            + "        \"mintemp_c\": 15.0,"
            + "        \"avgtemp_c\": 17.5,"
            + "        \"maxwind_kph\": 15.1,"
            + "        \"totalprecip_mm\": 0.0,"
            + "        \"avghumidity\": 70,"
            + "        \"condition\": {\"text\": \"Sunny\", \"code\": 1000}"
            + "      }"
            + "    },"
            + "    {"
            + "      \"date\": \"2024-01-16\","
            + "      \"day\": {"
            + "        \"maxtemp_c\": 19.0,"
            + "        \"mintemp_c\": 14.0,"
            + "        \"avgtemp_c\": 16.5,"
            + "        \"maxwind_kph\": 12.5,"
            + "        \"totalprecip_mm\": 2.3,"
            + "        \"avghumidity\": 75,"
            + "        \"condition\": {\"text\": \"Rainy\", \"code\": 1180}"
            + "      }"
            + "    }"
            + "  ]"
            + "}"
            + "}";

        // Validate location
        JsonAssertX.assertThat(weather)
            .path("$.location.name").asString().isEqualTo("San Francisco")
            .path("$.location.lat").asNumber().isBetween(-90, 90)
            .path("$.location.lon").asNumber().isBetween(-180, 180);

        // Validate current conditions
        JsonAssertX.assertThat(weather)
            .path("$.current.temp_c").asNumber().isBetween(-50, 50)
            .path("$.current.humidity").asNumber().isBetween(0, 100)
            .path("$.current.is_day").asNumber().isBetween(0, 1)
            .path("$.current.condition").asObject().hasKeys("text", "icon", "code")
            .path("$.current.wind_kph").asNumber().isPositive();

        // Validate forecast array
        JsonAssertX.assertThat(weather)
            .path("$.forecast.forecastday").asArray()
            .hasSize(2)
            .allMatch(day -> {
                if (day instanceof com.fasterxml.jackson.databind.JsonNode) {
                    com.fasterxml.jackson.databind.JsonNode node = (com.fasterxml.jackson.databind.JsonNode) day;
                    if (!node.has("date") || !node.has("day")) return false;
                    com.fasterxml.jackson.databind.JsonNode dayNode = node.get("day");
                    double maxTemp = dayNode.get("maxtemp_c").asDouble();
                    double minTemp = dayNode.get("mintemp_c").asDouble();
                    return maxTemp > minTemp;
                }
                return false;
            });

        // Validate specific forecast days
        JsonAssertX.assertThat(weather)
            .path("$.forecast.forecastday[0].date").asString().matches("\\d{4}-\\d{2}-\\d{2}")
            .path("$.forecast.forecastday[0].day.maxtemp_c").asNumber().isGreaterThan(15)
            .path("$.forecast.forecastday[1].day.totalprecip_mm").asNumber().isPositive();
    }

    @Test
    @DisplayName("Social Media Post - Complex array filtering")
    void testSocialMediaPostApiResponse() {
        String post = "{"
            + "\"postId\": \"POST-123456\","
            + "\"author\": {"
            + "  \"userId\": \"USER-789\","
            + "  \"username\": \"john_doe\","
            + "  \"displayName\": \"John Doe\","
            + "  \"verified\": true,"
            + "  \"followers\": 15420"
            + "},"
            + "\"content\": {"
            + "  \"text\": \"Check out this amazing library! #JSONAssertX #Testing #Java\","
            + "  \"hashtags\": [\"JSONAssertX\", \"Testing\", \"Java\"],"
            + "  \"mentions\": [\"@testuser\", \"@developer123\"],"
            + "  \"media\": ["
            + "    {\"type\": \"image\", \"url\": \"https://example.com/image1.jpg\", \"width\": 1920, \"height\": 1080},"
            + "    {\"type\": \"image\", \"url\": \"https://example.com/image2.jpg\", \"width\": 1024, \"height\": 768}"
            + "  ]"
            + "},"
            + "\"engagement\": {"
            + "  \"likes\": 1250,"
            + "  \"comments\": 87,"
            + "  \"shares\": 42,"
            + "  \"views\": 8500"
            + "},"
            + "\"metadata\": {"
            + "  \"createdAt\": \"2024-01-15T09:00:00Z\","
            + "  \"updatedAt\": \"2024-01-15T09:15:00Z\","
            + "  \"edited\": true,"
            + "  \"language\": \"en\","
            + "  \"visibility\": \"public\""
            + "},"
            + "\"comments\": ["
            + "  {"
            + "    \"commentId\": \"CMT-001\","
            + "    \"author\": \"alice_smith\","
            + "    \"text\": \"Great post!\","
            + "    \"likes\": 23,"
            + "    \"timestamp\": \"2024-01-15T09:05:00Z\""
            + "  },"
            + "  {"
            + "    \"commentId\": \"CMT-002\","
            + "    \"author\": \"bob_jones\","
            + "    \"text\": \"Thanks for sharing!\","
            + "    \"likes\": 15,"
            + "    \"timestamp\": \"2024-01-15T09:10:00Z\""
            + "  }"
            + "]"
            + "}";

        // Validate author
        JsonAssertX.assertThat(post)
            .path("$.author").asObject().hasKeys("userId", "username", "displayName", "verified")
            .path("$.author.verified").asBoolean().isTrue()
            .path("$.author.followers").asNumber().isGreaterThan(1000);

        // Validate content
        JsonAssertX.assertThat(post)
            .path("$.content.text").asString().contains("#JSONAssertX")
            .path("$.content.hashtags").asArray().hasSize(3)
            .path("$.content.hashtags").asArray().contains("Testing")
            .path("$.content.mentions").asArray().hasSize(2);

        // Validate media
        JsonAssertX.assertThat(post)
            .path("$.content.media").asArray()
            .hasSize(2)
            .allMatch(media -> {
                if (media instanceof com.fasterxml.jackson.databind.JsonNode) {
                    com.fasterxml.jackson.databind.JsonNode node = (com.fasterxml.jackson.databind.JsonNode) media;
                    return node.get("type").asText().equals("image") &&
                           node.get("url").asText().startsWith("https://");
                }
                return false;
            });

        // Validate engagement metrics
        JsonAssertX.assertThat(post)
            .path("$.engagement.likes").asNumber().isGreaterThan(1000)
            .path("$.engagement.comments").asNumber().isPositive()
            .path("$.engagement.views").asNumber().isGreaterThan(5000);

        // Validate metadata
        JsonAssertX.assertThat(post)
            .path("$.metadata.edited").asBoolean().isTrue()
            .path("$.metadata.language").asString().hasLength(2)
            .path("$.metadata.visibility").asString().isEqualTo("public");

        // Validate comments array
        JsonAssertX.assertThat(post)
            .path("$.comments").asArray()
            .hasSize(2)
            .allMatch(comment -> {
                if (comment instanceof com.fasterxml.jackson.databind.JsonNode) {
                    com.fasterxml.jackson.databind.JsonNode node = (com.fasterxml.jackson.databind.JsonNode) comment;
                    return node.has("commentId") && 
                           node.has("author") && 
                           node.get("likes").asInt() > 0;
                }
                return false;
            });
    }

    @Test
    @DisplayName("Microservice Health Check - Service status validation")
    void testMicroserviceHealthCheckResponse() {
        String healthCheck = "{"
            + "\"status\": \"UP\","
            + "\"timestamp\": \"2024-01-15T10:30:00Z\","
            + "\"services\": {"
            + "  \"database\": {"
            + "    \"status\": \"UP\","
            + "    \"responseTime\": 45,"
            + "    \"connections\": {\"active\": 12, \"idle\": 8, \"max\": 100}"
            + "  },"
            + "  \"cache\": {"
            + "    \"status\": \"UP\","
            + "    \"responseTime\": 2,"
            + "    \"hitRate\": 0.95,"
            + "    \"memoryUsage\": {\"used\": 512, \"max\": 2048, \"unit\": \"MB\"}"
            + "  },"
            + "  \"messageQueue\": {"
            + "    \"status\": \"UP\","
            + "    \"responseTime\": 8,"
            + "    \"queueDepth\": 245,"
            + "    \"consumers\": 5"
            + "  },"
            + "  \"externalApi\": {"
            + "    \"status\": \"DEGRADED\","
            + "    \"responseTime\": 1500,"
            + "    \"successRate\": 0.85,"
            + "    \"lastError\": \"Timeout on 3 requests\""
            + "  }"
            + "},"
            + "\"metrics\": {"
            + "  \"cpuUsage\": 0.65,"
            + "  \"memoryUsage\": 0.72,"
            + "  \"diskUsage\": 0.45,"
            + "  \"requestsPerSecond\": 1250,"
            + "  \"averageResponseTime\": 125"
            + "},"
            + "\"version\": \"1.2.3\","
            + "\"uptime\": 2592000"
            + "}";

        // Validate overall status
        JsonAssertX.assertThat(healthCheck)
            .path("$.status").asString().isEqualTo("UP")
            .path("$.timestamp").asString().matches("\\d{4}-\\d{2}-\\d{2}T.*")
            .path("$.version").asString().matches("\\d+\\.\\d+\\.\\d+");

        // Validate database service
        JsonAssertX.assertThat(healthCheck)
            .path("$.services.database.status").asString().isEqualTo("UP")
            .path("$.services.database.responseTime").asNumber().isLessThan(100)
            .path("$.services.database.connections.active").asNumber().isPositive()
            .path("$.services.database.connections.max").asNumber().isGreaterThan(50);

        // Validate cache service
        JsonAssertX.assertThat(healthCheck)
            .path("$.services.cache.status").asString().isEqualTo("UP")
            .path("$.services.cache.hitRate").asNumber().isBetween(0, 1)
            .path("$.services.cache.memoryUsage.used").asNumber().isLessThan(2048);

        // Validate degraded external API
        JsonAssertX.assertThat(healthCheck)
            .path("$.services.externalApi.status").asString().isEqualTo("DEGRADED")
            .path("$.services.externalApi.responseTime").asNumber().isGreaterThan(1000)
            .path("$.services.externalApi.successRate").asNumber().isBetween(0, 1)
            .hasPath("$.services.externalApi.lastError");

        // Validate metrics
        JsonAssertX.assertThat(healthCheck)
            .path("$.metrics.cpuUsage").asNumber().isBetween(0, 1)
            .path("$.metrics.memoryUsage").asNumber().isBetween(0, 1)
            .path("$.metrics.requestsPerSecond").asNumber().isPositive()
            .path("$.metrics.averageResponseTime").asNumber().isLessThan(500);

        // Validate uptime
        JsonAssertX.assertThat(healthCheck)
            .path("$.uptime").asNumber().isGreaterThan(0);
    }
}
