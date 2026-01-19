package io.jsonassertx.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Utility class for formatting JSON differences in a visual, human-readable way.
 * 
 * @since 1.0.0
 */
public class JsonDiffFormatter {

    private static final ObjectMapper PRETTY_MAPPER = new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Formats a difference between expected and actual values with visual highlighting.
     */
    public static String formatDifference(String path, Object expected, Object actual) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("  JSON ASSERTION FAILED\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("\nPath: ").append(path).append("\n");
        
        if (expected instanceof String && actual instanceof String) {
            formatStringDiff(sb, (String) expected, (String) actual);
        } else if (expected instanceof Number && actual instanceof Number) {
            formatNumberDiff(sb, (Number) expected, (Number) actual);
        } else {
            formatGenericDiff(sb, expected, actual);
        }
        
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Formats a string difference with character-level highlighting.
     */
    private static void formatStringDiff(StringBuilder sb, String expected, String actual) {
        sb.append("\n");
        sb.append("Expected: \"").append(expected).append("\"\n");
        sb.append("Actual:   \"").append(actual).append("\"\n");
        
        // Find first difference
        int diffPos = -1;
        int minLen = Math.min(expected.length(), actual.length());
        for (int i = 0; i < minLen; i++) {
            if (expected.charAt(i) != actual.charAt(i)) {
                diffPos = i;
                break;
            }
        }
        
        if (diffPos >= 0) {
            sb.append("          ");
            sb.append(" ".repeat(diffPos + 1)); // +1 for opening quote
            sb.append("^ First difference at position ").append(diffPos).append("\n");
        } else if (expected.length() != actual.length()) {
            sb.append("\n");
            if (expected.length() > actual.length()) {
                sb.append("⚠ Actual string is shorter by ").append(expected.length() - actual.length()).append(" character(s)\n");
            } else {
                sb.append("⚠ Actual string is longer by ").append(actual.length() - expected.length()).append(" character(s)\n");
            }
        }
        
        // Show whitespace issues if present
        if (expected.trim().equals(actual.trim()) && !expected.equals(actual)) {
            sb.append("\n💡 Strings differ only in whitespace\n");
            sb.append("   Expected (showing spaces as ·): ").append(expected.replace(' ', '·')).append("\n");
            sb.append("   Actual   (showing spaces as ·): ").append(actual.replace(' ', '·')).append("\n");
        }
    }

    /**
     * Formats a number difference.
     */
    private static void formatNumberDiff(StringBuilder sb, Number expected, Number actual) {
        sb.append("\n");
        sb.append("Expected: ").append(expected).append("\n");
        sb.append("Actual:   ").append(actual).append("\n");
        
        double diff = Math.abs(expected.doubleValue() - actual.doubleValue());
        sb.append("\nDifference: ").append(diff);
        
        if (diff < 0.01) {
            sb.append(" (very small difference - possible precision issue)");
        }
        sb.append("\n");
    }

    /**
     * Formats a generic object difference.
     */
    private static void formatGenericDiff(StringBuilder sb, Object expected, Object actual) {
        sb.append("\n");
        sb.append("Expected:\n");
        sb.append("  ").append(formatValue(expected)).append("\n");
        sb.append("\nActual:\n");
        sb.append("  ").append(formatValue(actual)).append("\n");
    }

    /**
     * Formats a JSON comparison with structural highlighting.
     */
    public static String formatJsonDifference(JsonNode expected, JsonNode actual) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("  JSON STRUCTURE MISMATCH\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        
        List<String> differences = findDifferences("$", expected, actual);
        
        if (!differences.isEmpty()) {
            sb.append("\nDifferences found:\n");
            for (String diff : differences) {
                sb.append("  ✗ ").append(diff).append("\n");
            }
        }
        
        sb.append("\nExpected JSON:\n");
        sb.append(prettyPrint(expected)).append("\n");
        
        sb.append("\nActual JSON:\n");
        sb.append(prettyPrint(actual)).append("\n");
        
        return sb.toString();
    }

    /**
     * Finds all differences between two JSON nodes.
     */
    private static List<String> findDifferences(String path, JsonNode expected, JsonNode actual) {
        List<String> differences = new ArrayList<>();
        
        if (expected == null && actual == null) {
            return differences;
        }
        
        if (expected == null) {
            differences.add(path + ": expected null but got " + actual.getNodeType());
            return differences;
        }
        
        if (actual == null) {
            differences.add(path + ": expected " + expected.getNodeType() + " but got null");
            return differences;
        }
        
        if (!expected.getNodeType().equals(actual.getNodeType())) {
            differences.add(path + ": type mismatch - expected " + expected.getNodeType() + " but got " + actual.getNodeType());
            return differences;
        }
        
        if (expected.isObject()) {
            // Check for missing fields
            Iterator<String> expectedFields = expected.fieldNames();
            while (expectedFields.hasNext()) {
                String fieldName = expectedFields.next();
                if (!actual.has(fieldName)) {
                    differences.add(path + "." + fieldName + ": missing in actual JSON");
                } else {
                    differences.addAll(findDifferences(path + "." + fieldName, 
                        expected.get(fieldName), actual.get(fieldName)));
                }
            }
            
            // Check for extra fields
            Iterator<String> actualFields = actual.fieldNames();
            while (actualFields.hasNext()) {
                String fieldName = actualFields.next();
                if (!expected.has(fieldName)) {
                    differences.add(path + "." + fieldName + ": unexpected field in actual JSON");
                }
            }
        } else if (expected.isArray()) {
            if (expected.size() != actual.size()) {
                differences.add(path + ": array size mismatch - expected " + expected.size() + " but got " + actual.size());
            }
            
            int minSize = Math.min(expected.size(), actual.size());
            for (int i = 0; i < minSize; i++) {
                differences.addAll(findDifferences(path + "[" + i + "]", 
                    expected.get(i), actual.get(i)));
            }
        } else if (!expected.equals(actual)) {
            differences.add(path + ": value mismatch - expected '" + expected.asText() + "' but got '" + actual.asText() + "'");
        }
        
        return differences;
    }

    /**
     * Formats an array size mismatch.
     */
    public static String formatArraySizeMismatch(int expectedSize, int actualSize, List<?> elements) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("  ARRAY SIZE MISMATCH\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("\nExpected: ").append(expectedSize).append(" element(s)\n");
        sb.append("Actual:   ").append(actualSize).append(" element(s)\n");
        
        if (actualSize > expectedSize) {
            sb.append("\n⚠ Array has ").append(actualSize - expectedSize).append(" extra element(s)\n");
        } else {
            sb.append("\n⚠ Array is missing ").append(expectedSize - actualSize).append(" element(s)\n");
        }
        
        sb.append("\nActual array content:\n");
        sb.append("[\n");
        for (int i = 0; i < elements.size(); i++) {
            sb.append("  [").append(i).append("] ");
            if (i >= expectedSize) {
                sb.append("➜ "); // Mark extra elements
            }
            sb.append(formatValue(elements.get(i))).append("\n");
        }
        sb.append("]\n");
        
        return sb.toString();
    }

    /**
     * Formats a missing element error.
     */
    public static String formatMissingElement(Object element, List<?> array) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("  ELEMENT NOT FOUND IN ARRAY\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("\nLooking for: ").append(formatValue(element)).append("\n");
        sb.append("\nArray contents (").append(array.size()).append(" element(s)):\n");
        
        if (array.isEmpty()) {
            sb.append("  (empty array)\n");
        } else {
            for (int i = 0; i < array.size(); i++) {
                sb.append("  [").append(i).append("] ").append(formatValue(array.get(i))).append("\n");
            }
        }
        
        // Suggest similar values
        if (element instanceof String) {
            String target = (String) element;
            sb.append("\nSimilar values in array:\n");
            boolean foundSimilar = false;
            for (Object item : array) {
                if (item instanceof String) {
                    String itemStr = (String) item;
                    if (itemStr.toLowerCase().contains(target.toLowerCase()) || 
                        target.toLowerCase().contains(itemStr.toLowerCase())) {
                        sb.append("  • ").append(itemStr).append("\n");
                        foundSimilar = true;
                    }
                }
            }
            if (!foundSimilar) {
                sb.append("  (no similar values found)\n");
            }
        }
        
        return sb.toString();
    }

    /**
     * Formats a predicate match failure.
     */
    public static String formatPredicateFailure(String predicateType, int failedIndex, Object element, List<?> array) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("  PREDICATE ").append(predicateType.toUpperCase()).append(" FAILED\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        
        if ("allMatch".equals(predicateType)) {
            sb.append("\nExpected all elements to match predicate\n");
            sb.append("Failed at index: ").append(failedIndex).append("\n");
            sb.append("Element: ").append(formatValue(element)).append("\n");
        } else if ("anyMatch".equals(predicateType)) {
            sb.append("\nExpected at least one element to match predicate\n");
            sb.append("No matching elements found in array of ").append(array.size()).append(" element(s)\n");
        } else if ("noneMatch".equals(predicateType)) {
            sb.append("\nExpected no elements to match predicate\n");
            sb.append("Matched at index: ").append(failedIndex).append("\n");
            sb.append("Element: ").append(formatValue(element)).append("\n");
        }
        
        return sb.toString();
    }

    /**
     * Formats a path not found error with context.
     */
    public static String formatPathNotFound(String path, String json) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("  PATH NOT FOUND\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("\nPath: ").append(path).append("\n");
        
        // Try to provide helpful context
        String[] pathParts = path.replace("$.", "").split("\\.");
        if (pathParts.length > 0) {
            sb.append("\nPath breakdown:\n");
            StringBuilder partialPath = new StringBuilder("$");
            for (String part : pathParts) {
                partialPath.append(".").append(part);
                sb.append("  ").append(partialPath).append("\n");
            }
        }
        
        sb.append("\nJSON structure:\n");
        sb.append(formatCompactJson(json));
        
        return sb.toString();
    }

    /**
     * Pretty prints a JSON node.
     */
    private static String prettyPrint(JsonNode node) {
        try {
            return PRETTY_MAPPER.writeValueAsString(node);
        } catch (Exception e) {
            return node.toString();
        }
    }

    /**
     * Formats a value for display.
     */
    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "\"" + value + "\"";
        }
        if (value instanceof JsonNode) {
            return prettyPrint((JsonNode) value);
        }
        return value.toString();
    }

    /**
     * Formats JSON in a compact, readable way.
     */
    private static String formatCompactJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            return PRETTY_MAPPER.writeValueAsString(node);
        } catch (Exception e) {
            return json;
        }
    }
}
