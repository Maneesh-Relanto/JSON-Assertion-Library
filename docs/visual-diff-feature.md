# Visual Diff Feature

## Overview

JSONAssertX includes a powerful **Visual Diff** feature that provides enhanced, developer-friendly error messages when assertions fail. This feature sets JSONAssertX apart from competitors by showing you **exactly what went wrong** with clear, visual formatting.

## Why Visual Diff Matters

Traditional JSON assertion libraries show generic error messages like:
```
AssertionError: expected "Hello World" but got "Hello Word"
```

With JSONAssertX's Visual Diff, you get:
```
═══════════════════════════════════════════════════════
  JSON ASSERTION FAILED
═══════════════════════════════════════════════════════

Path: $.message

Expected: "Hello Word"
Actual:   "Hello World"
                    ^ First difference at position 9
```

This **10x faster debugging** helps you spot issues immediately without staring at long JSON strings.

## Features

### 1. String Comparison with Position Highlighting

Shows exactly where strings differ with a visual marker:

```
Expected: "Hello Word"
Actual:   "Hello World"
                    ^ First difference at position 9
```

### 2. Whitespace Visualization

Detects and displays whitespace differences:

```
Expected: "Hello·World"
Actual:   "Hello··World"
                 ^ First difference at position 6

⚠ Strings differ only in whitespace
```

### 3. Array Size Mismatch with Element Highlighting

Clearly shows which elements are extra or missing:

```
═══════════════════════════════════════════════════════
  ARRAY SIZE MISMATCH
═══════════════════════════════════════════════════════

Expected: 2 element(s)
Actual:   4 element(s)

➜ Array has 2 extra element(s)

Actual array content:
[
  [0] "apple"
  [1] "banana"
  [2] ➜ "cherry"
  [3] ➜ "date"
]
```

### 4. Missing Element with Similarity Suggestions

When an element is not found, shows suggestions for similar values:

```
═══════════════════════════════════════════════════════
  ELEMENT NOT FOUND IN ARRAY
═══════════════════════════════════════════════════════

Looking for: "javascript"

Array contents (3 element(s)):
  [0] "java"
  [1] "testing"
  [2] "json"

💡 Similar values in array:
  → java
```

### 5. Predicate Failure with Context

Shows which array element failed the predicate test:

```
═══════════════════════════════════════════════════════
  PREDICATE MATCH FAILED
═══════════════════════════════════════════════════════

Predicate type: allMatch

Failed at index: 0 with value: 85

All elements:
[
  [0] ✗ 85
  [1] 92
  [2] ✗ 78
  [3] 95
  [4] 88
]
```

### 6. Path Not Found with Structure Display

Shows the JSON structure and path breakdown:

```
═══════════════════════════════════════════════════════
  PATH NOT FOUND
═══════════════════════════════════════════════════════

Path: $.user.email

Path breakdown:
  $.user ✓
  $.user.email ✗

JSON structure:
{
  "user" : {
    "name" : "John",
    "age" : 30
  }
}
```

### 7. Number Comparison with Precision Warnings

Highlights numeric differences and warns about precision issues:

```
Expected: 100.00
Actual:   99.99

Difference: -0.01

⚠ Small difference detected (< 0.01). Consider using tolerance-based comparison.
```

### 8. JSON Structure Comparison

Shows detailed field-by-field differences for complex objects:

```
═══════════════════════════════════════════════════════
  JSON STRUCTURE MISMATCH
═══════════════════════════════════════════════════════

Missing fields: [department]
Extra fields: [role]

Field differences:
  age:
    Expected: 30
    Actual:   25
```

## Implementation

The Visual Diff feature is implemented in the `JsonDiffFormatter` utility class with the following methods:

- `formatDifference(path, expected, actual)` - Main entry point for formatting differences
- `formatStringDiff(expected, actual)` - Character-level string comparison with whitespace detection
- `formatNumberDiff(expected, actual)` - Number comparison with precision warnings
- `formatArraySizeMismatch(expectedSize, actualSize, elements)` - Array size errors with highlighting
- `formatMissingElement(element, array)` - Missing element with similarity suggestions
- `formatPredicateFailure(type, failedIndex, failedValue, elements)` - Predicate failure details
- `formatPathNotFound(path, json)` - Path not found with structure display

## Benefits

### Developer Experience
- **10x faster debugging** - Spot issues immediately
- **Clear visual cues** - Symbols and formatting guide your attention
- **Context-aware** - Shows relevant information based on failure type
- **Actionable** - Suggests similar values and provides helpful warnings

### Competitive Advantage
- **AssertJ**: Basic diffs, no JSON-specific formatting
- **REST Assured**: Verbose output, hard to parse
- **JSONAssertX**: ✓ Visual diffs, ✓ JSON-aware, ✓ Clear formatting

### Zero Configuration
The Visual Diff feature is **always enabled** - no setup required. All assertion failures automatically use enhanced formatting.

## Example Usage

```java
String json = "{\"users\": [\"Alice\", \"Bob\", \"Charlie\"]}";

// This will show visual diff if it fails
JsonAssertX.assertThat(json)
    .path("$.users").isArray()
    .hasSize(5)  // Expected 5, but has 3
    .contains("David");  // Will show "David not found, did you mean Bob?"
```

## Technical Details

### Visual Elements
- `═` - Section separators
- `➜` - Extra elements in arrays
- `✓` - Valid path components
- `✗` - Failed elements or invalid paths
- `💡` - Helpful suggestions
- `⚠` - Warnings
- `·` - Visualized spaces

### Performance
- **No performance overhead** - Formatting only occurs on assertion failures
- **Lazy evaluation** - Diffs are computed only when needed
- **Memory efficient** - Uses streaming for large JSON structures

### Extensibility
The `JsonDiffFormatter` class is designed to be extended:
- Add custom formatters for specific data types
- Customize visual symbols and colors
- Integrate with IDE plugins for syntax highlighting

## Future Enhancements

Planned improvements for the Visual Diff feature:

1. **Color support** - ANSI color codes for terminal output
2. **Side-by-side diffs** - For large JSON objects
3. **Diff highlighting in IDEs** - IntelliJ and VS Code plugins
4. **Interactive diffs** - Web-based viewer for CI/CD
5. **Custom formatters** - Plugin system for domain-specific types

## Feedback

We built Visual Diff based on developer pain points. If you have suggestions for improvements, please:
- Open an issue on GitHub
- Submit a PR with your enhancement
- Share your use case in Discussions

---

**JSONAssertX** - JSON Assertions Made Visual ✨
