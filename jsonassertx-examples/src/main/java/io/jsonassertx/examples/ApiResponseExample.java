package io.jsonassertx.examples;

import io.jsonassertx.JsonAssertX;

/**
 * Real-world API response testing examples.
 * Demonstrates how to validate typical REST API responses.
 */
public class ApiResponseExample {

    public static void main(String[] args) {
        githubApiExample();
        twitterApiExample();
        eCommerceApiExample();
        weatherApiExample();
    }

    /**
     * Validates a GitHub API user response.
     */
    private static void githubApiExample() {
        System.out.println("\n=== GitHub API Response Validation ===");
        
        String githubUser = "{"
            + "\"login\": \"octocat\","
            + "\"id\": 1,"
            + "\"avatar_url\": \"https://github.com/images/error/octocat_happy.gif\","
            + "\"type\": \"User\","
            + "\"name\": \"The Octocat\","
            + "\"company\": \"@github\","
            + "\"blog\": \"https://github.blog\","
            + "\"location\": \"San Francisco\","
            + "\"email\": null,"
            + "\"hireable\": null,"
            + "\"bio\": \"GitHub mascot\","
            + "\"public_repos\": 8,"
            + "\"followers\": 9999,"
            + "\"following\": 9"
            + "}";
        
        // Validate basic user properties
        JsonAssertX.assertThat(githubUser)
            .path("$.login").asString()
            .isEqualTo("octocat");
        System.out.println("✓ User login is 'octocat'");
        
        JsonAssertX.assertThat(githubUser)
            .path("$.type").asString()
            .isEqualTo("User");
        System.out.println("✓ Type is 'User'");
        
        // Validate URLs
        JsonAssertX.assertThat(githubUser)
            .path("$.blog").asString()
            .startsWith("https://");
        System.out.println("✓ Blog URL uses HTTPS");
        
        JsonAssertX.assertThat(githubUser)
            .path("$.avatar_url").asString()
            .contains("github.com");
        System.out.println("✓ Avatar URL is from GitHub");
        
        // Validate numeric properties
        JsonAssertX.assertThat(githubUser)
            .path("$.public_repos").asNumber()
            .isGreaterThan(0);
        System.out.println("✓ User has public repos");
        
        JsonAssertX.assertThat(githubUser)
            .path("$.followers").asNumber()
            .isGreaterThan(1000);
        System.out.println("✓ User has over 1000 followers");
    }

    /**
     * Validates a Twitter API tweet response.
     */
    private static void twitterApiExample() {
        System.out.println("\n=== Twitter API Response Validation ===");
        
        String tweet = "{"
            + "\"id\": 1234567890,"
            + "\"text\": \"Hello World! #testing @jsonassertx\","
            + "\"created_at\": \"2024-01-15T10:30:00Z\","
            + "\"user\": {"
            + "  \"id\": 987654321,"
            + "  \"screen_name\": \"testuser\","
            + "  \"verified\": true,"
            + "  \"followers_count\": 5000"
            + "},"
            + "\"entities\": {"
            + "  \"hashtags\": [{\"text\": \"testing\"}],"
            + "  \"mentions\": [{\"screen_name\": \"jsonassertx\"}]"
            + "},"
            + "\"retweet_count\": 42,"
            + "\"favorite_count\": 128"
            + "}";
        
        // Validate tweet content
        JsonAssertX.assertThat(tweet)
            .path("$.text").asString()
            .contains("Hello World");
        System.out.println("✓ Tweet contains 'Hello World'");
        
        JsonAssertX.assertThat(tweet)
            .path("$.text").asString()
            .contains("#testing");
        System.out.println("✓ Tweet has #testing hashtag");
        
        // Validate user properties
        JsonAssertX.assertThat(tweet)
            .path("$.user.screen_name").asString()
            .isEqualTo("testuser");
        System.out.println("✓ User is 'testuser'");
        
        JsonAssertX.assertThat(tweet)
            .path("$.user.verified").asBoolean()
            .isTrue();
        System.out.println("✓ User is verified");
        
        // Validate entities
        JsonAssertX.assertThat(tweet)
            .path("$.entities.hashtags").asArray()
            .hasSize(1);
        System.out.println("✓ Tweet has 1 hashtag");
        
        JsonAssertX.assertThat(tweet)
            .path("$.entities.mentions[0].screen_name").asString()
            .isEqualTo("jsonassertx");
        System.out.println("✓ Tweet mentions 'jsonassertx'");
        
        // Validate engagement metrics
        JsonAssertX.assertThat(tweet)
            .path("$.retweet_count").asNumber()
            .isGreaterThan(0);
        System.out.println("✓ Tweet has retweets");
        
        JsonAssertX.assertThat(tweet)
            .path("$.favorite_count").asNumber()
            .isGreaterThan(100);
        System.out.println("✓ Tweet has over 100 favorites");
    }

    /**
     * Validates an e-commerce API order response.
     */
    private static void eCommerceApiExample() {
        System.out.println("\n=== E-Commerce API Response Validation ===");
        
        String order = "{"
            + "\"orderId\": \"ORD-12345\","
            + "\"status\": \"completed\","
            + "\"customer\": {"
            + "  \"id\": 54321,"
            + "  \"email\": \"customer@example.com\","
            + "  \"name\": \"Jane Smith\""
            + "},"
            + "\"items\": ["
            + "  {\"productId\": \"P001\", \"name\": \"Laptop\", \"quantity\": 1, \"price\": 999.99},"
            + "  {\"productId\": \"P002\", \"name\": \"Mouse\", \"quantity\": 2, \"price\": 29.99}"
            + "],"
            + "\"subtotal\": 1059.97,"
            + "\"tax\": 84.80,"
            + "\"shipping\": 15.00,"
            + "\"total\": 1159.77,"
            + "\"paymentMethod\": \"credit_card\","
            + "\"shippingAddress\": {"
            + "  \"street\": \"123 Main St\","
            + "  \"city\": \"Boston\","
            + "  \"state\": \"MA\","
            + "  \"zip\": \"02101\","
            + "  \"country\": \"USA\""
            + "}"
            + "}";
        
        // Validate order status
        JsonAssertX.assertThat(order)
            .path("$.status").asString()
            .isEqualTo("completed");
        System.out.println("✓ Order is completed");
        
        // Validate customer email format
        JsonAssertX.assertThat(order)
            .path("$.customer.email").asString()
            .matches("^[a-z]+@[a-z]+\\.[a-z]+$");
        System.out.println("✓ Customer email is valid");
        
        // Validate order items
        JsonAssertX.assertThat(order)
            .path("$.items").asArray()
            .hasSize(2);
        System.out.println("✓ Order has 2 items");
        
        JsonAssertX.assertThat(order)
            .path("$.items[0].name").asString()
            .isEqualTo("Laptop");
        System.out.println("✓ First item is a Laptop");
        
        // Validate pricing
        JsonAssertX.assertThat(order)
            .path("$.subtotal").asNumber()
            .isGreaterThan(1000);
        System.out.println("✓ Subtotal is over $1000");
        
        JsonAssertX.assertThat(order)
            .path("$.total").asNumber()
            .isGreaterThan(1100);
        System.out.println("✓ Total is over $1100");
        
        // Validate shipping address structure
        JsonAssertX.assertThat(order)
            .path("$.shippingAddress").isObject()
            .hasKeys("street", "city", "state", "zip", "country");
        System.out.println("✓ Shipping address has all required fields");
        
        JsonAssertX.assertThat(order)
            .path("$.shippingAddress.country").asString()
            .isEqualTo("USA");
        System.out.println("✓ Ships to USA");
    }

    /**
     * Validates a weather API response.
     */
    private static void weatherApiExample() {
        System.out.println("\n=== Weather API Response Validation ===");
        
        String weather = "{"
            + "\"location\": {"
            + "  \"city\": \"San Francisco\","
            + "  \"country\": \"USA\","
            + "  \"lat\": 37.7749,"
            + "  \"lon\": -122.4194"
            + "},"
            + "\"current\": {"
            + "  \"temperature\": 18.5,"
            + "  \"feels_like\": 17.0,"
            + "  \"humidity\": 72,"
            + "  \"pressure\": 1013,"
            + "  \"wind_speed\": 5.5,"
            + "  \"condition\": \"Partly Cloudy\""
            + "},"
            + "\"forecast\": ["
            + "  {\"day\": \"Monday\", \"high\": 20, \"low\": 15, \"condition\": \"Sunny\"},"
            + "  {\"day\": \"Tuesday\", \"high\": 19, \"low\": 14, \"condition\": \"Cloudy\"},"
            + "  {\"day\": \"Wednesday\", \"high\": 18, \"low\": 13, \"condition\": \"Rainy\"}"
            + "]"
            + "}";
        
        // Validate location
        JsonAssertX.assertThat(weather)
            .path("$.location.city").asString()
            .isEqualTo("San Francisco");
        System.out.println("✓ Location is San Francisco");
        
        // Validate temperature ranges
        JsonAssertX.assertThat(weather)
            .path("$.current.temperature").asNumber()
            .isBetween(-50, 50);
        System.out.println("✓ Temperature is in valid range");
        
        JsonAssertX.assertThat(weather)
            .path("$.current.feels_like").asNumber()
            .isLessThan(30);
        System.out.println("✓ Feels like temperature is reasonable");
        
        // Validate humidity percentage
        JsonAssertX.assertThat(weather)
            .path("$.current.humidity").asNumber()
            .isBetween(0, 100);
        System.out.println("✓ Humidity is valid percentage");
        
        // Validate wind speed
        JsonAssertX.assertThat(weather)
            .path("$.current.wind_speed").asNumber()
            .isPositive();
        System.out.println("✓ Wind speed is positive");
        
        // Validate forecast
        JsonAssertX.assertThat(weather)
            .path("$.forecast").asArray()
            .hasSize(3);
        System.out.println("✓ Forecast has 3 days");
        
        JsonAssertX.assertThat(weather)
            .path("$.forecast[0].day").asString()
            .isEqualTo("Monday");
        System.out.println("✓ First forecast day is Monday");
        
        // Validate forecast temperatures make sense
        JsonAssertX.assertThat(weather)
            .path("$.forecast").asArray()
            .allMatch(day -> {
                Object highObj = ((java.util.Map<?, ?>) day).get("high");
                Object lowObj = ((java.util.Map<?, ?>) day).get("low");
                int high = highObj instanceof Number ? ((Number) highObj).intValue() : 0;
                int low = lowObj instanceof Number ? ((Number) lowObj).intValue() : 0;
                return high > low;
            }, "high temp > low temp");
        System.out.println("✓ All forecast days have high > low");
        
        System.out.println("\n=== All API Examples Passed! ===\n");
    }
}
