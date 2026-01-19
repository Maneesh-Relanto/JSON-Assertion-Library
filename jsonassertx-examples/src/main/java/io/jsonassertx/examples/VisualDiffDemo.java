package io.jsonassertx.examples;

import io.jsonassertx.JsonAssertX;

/**
 * Interactive demo showcasing JSONAssertX's Visual Diff feature.
 * 
 * This demo intentionally triggers assertion failures to demonstrate
 * the enhanced error messages with visual formatting.
 * 
 * Run this class to see visual diffs in action!
 */
public class VisualDiffDemo {

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║  JSONAssertX - Visual Diff Feature Demo                      ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("This demo will intentionally fail assertions to show you");
        System.out.println("the beautiful visual diff error messages.");
        System.out.println();
        
        runDemo1_StringComparison();
        runDemo2_ArraySizeMismatch();
        runDemo3_MissingElement();
        runDemo4_PathNotFound();
        runDemo5_PredicateFailure();
        runDemo6_WhitespaceDifference();
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║  Demo Complete!                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println("\nAll demos above intentionally failed to showcase visual diffs.");
        System.out.println("In your real tests, these enhanced messages help you debug 10x faster!");
    }

    private static void runDemo1_StringComparison() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Demo 1: String Comparison with Position Highlighting");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        String json = "{\"message\": \"Hello World\"}";
        
        try {
            JsonAssertX.assertThat(json)
                .path("$.message").isString()
                .isEqualTo("Hello Word"); // Missing 'l' in World
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println();
        waitForUser();
    }

    private static void runDemo2_ArraySizeMismatch() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Demo 2: Array Size Mismatch with Element Highlighting");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        String json = "{\"items\": [\"apple\", \"banana\", \"cherry\", \"date\", \"elderberry\"]}";
        
        try {
            JsonAssertX.assertThat(json)
                .path("$.items").isArray()
                .hasSize(3); // Expected 3, but has 5
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println();
        waitForUser();
    }

    private static void runDemo3_MissingElement() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Demo 3: Missing Element with Similarity Suggestions");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        String json = "{\"languages\": [\"Java\", \"Python\", \"JavaScript\", \"TypeScript\", \"Go\"]}";
        
        try {
            JsonAssertX.assertThat(json)
                .path("$.languages").isArray()
                .contains("Rust"); // Not in the array, but might suggest "JavaScript"
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println();
        waitForUser();
    }

    private static void runDemo4_PathNotFound() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Demo 4: Path Not Found with JSON Structure Display");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        String json = "{\"user\": {\"name\": \"Alice\", \"age\": 30, \"role\": \"admin\"}}";
        
        try {
            JsonAssertX.assertThat(json)
                .hasPath("$.user.email"); // Path doesn't exist
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println();
        waitForUser();
    }

    private static void runDemo5_PredicateFailure() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Demo 5: Predicate Failure with Failed Element Context");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        String json = "{\"scores\": [95, 87, 92, 78, 88, 91]}";
        
        try {
            JsonAssertX.assertThat(json)
                .path("$.scores").isArray()
                .allMatch(score -> ((com.fasterxml.jackson.databind.JsonNode) score).asInt() >= 90);
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println();
        waitForUser();
    }

    private static void runDemo6_WhitespaceDifference() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Demo 6: Whitespace Difference Detection");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        String json = "{\"text\": \"Hello  World\"}"; // Two spaces between Hello and World
        
        try {
            JsonAssertX.assertThat(json)
                .path("$.text").isString()
                .isEqualTo("Hello World"); // One space
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println();
        waitForUser();
    }

    private static void waitForUser() {
        System.out.println("Press Enter to continue to the next demo...");
        try {
            System.in.read();
            // Clear the input buffer
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception e) {
            // Ignore
        }
        System.out.println();
    }
}
