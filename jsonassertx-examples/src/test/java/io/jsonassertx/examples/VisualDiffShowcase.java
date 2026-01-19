package io.jsonassertx.examples;

import org.junit.jupiter.api.Test;
import io.jsonassertx.JsonAssertX;

/**
 * Demo tests showcasing JSONAssertX's Visual Diff feature.
 * 
 * These tests intentionally fail to demonstrate the enhanced error messages.
 * Run this test class to see visual diffs in action!
 * 
 * Usage: mvn test -Dtest=VisualDiffShowcase
 */
public class VisualDiffShowcase {

    @Test
    public void demo1_stringComparison_showsPositionHighlighting() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  Demo 1: String Comparison with Position Highlighting         ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        String json = "{\"message\": \"Hello World\"}";
        
        // This will fail and show character-level diff with position marker
        JsonAssertX.assertThat(json)
            .path("$.message").isString()
            .isEqualTo("Hello Word"); // Missing 'l' in World
    }

    @Test
    public void demo2_arraySizeMismatch_highlightsExtraElements() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  Demo 2: Array Size Mismatch with Element Highlighting        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        String json = "{\"items\": [\"apple\", \"banana\", \"cherry\", \"date\", \"elderberry\"]}";
        
        // This will fail and mark extra elements with ➜ symbol
        JsonAssertX.assertThat(json)
            .path("$.items").isArray()
            .hasSize(3); // Expected 3, but has 5
    }

    @Test
    public void demo3_missingElement_suggestsSimilarValues() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  Demo 3: Missing Element with Similarity Suggestions          ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        String json = "{\"languages\": [\"Java\", \"Python\", \"JavaScript\", \"TypeScript\", \"Go\"]}";
        
        // This will fail and suggest similar values from the array
        JsonAssertX.assertThat(json)
            .path("$.languages").isArray()
            .contains("Rust"); // Not in array, might suggest "JavaScript"
    }

    @Test
    public void demo4_pathNotFound_showsJsonStructure() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  Demo 4: Path Not Found with JSON Structure Display           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        String json = "{\"user\": {\"name\": \"Alice\", \"age\": 30, \"role\": \"admin\"}}";
        
        // This will fail and show the actual JSON structure with path breakdown
        JsonAssertX.assertThat(json)
            .hasPath("$.user.email"); // Path doesn't exist
    }

    @Test
    public void demo5_predicateFailure_showsFailedElement() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  Demo 5: Predicate Failure with Failed Element Context        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        String json = "{\"scores\": [95, 87, 92, 78, 88, 91]}";
        
        // This will fail and show which elements failed the predicate
        JsonAssertX.assertThat(json)
            .path("$.scores").isArray()
            .allMatch(score -> ((com.fasterxml.jackson.databind.JsonNode) score).asInt() >= 90);
    }

    @Test
    public void demo6_whitespaceDifference_visualizesSpaces() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  Demo 6: Whitespace Difference Detection                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        String json = "{\"text\": \"Hello  World\"}"; // Two spaces
        
        // This will fail and visualize whitespace as · symbols
        JsonAssertX.assertThat(json)
            .path("$.text").isString()
            .isEqualTo("Hello World"); // One space
    }

    @Test
    public void demo7_numberComparison_showsPrecisionWarning() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  Demo 7: Number Comparison with Precision Warning             ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        String json = "{\"price\": 99.99}";
        
        // This will fail and warn about precision if difference is small
        JsonAssertX.assertThat(json)
            .path("$.price").isNumber()
            .isEqualTo(100.00);
    }

    @Test
    public void demo8_noneMatch_showsUnexpectedMatch() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  Demo 8: NoneMatch Failure Shows Unexpected Match              ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        String json = "{\"codes\": [\"A001\", \"B002\", \"C003\", \"D004\"]}";
        
        // This will fail and show which element unexpectedly matched
        JsonAssertX.assertThat(json)
            .path("$.codes").isArray()
            .noneMatch(code -> ((com.fasterxml.jackson.databind.JsonNode) code).asText().startsWith("C"));
    }
}
