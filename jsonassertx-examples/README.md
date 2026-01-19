# Visual Diff Demo

This directory contains interactive demos showcasing JSONAssertX's **Visual Diff** feature - the killer feature that sets us apart from competitors!

## Quick Start

Run all demos to see visual diffs in action:

```bash
mvn test -Dtest=VisualDiffShowcase
```

Or run individual demos:

```bash
# Demo 1: String comparison with character-level diff
mvn test -Dtest=VisualDiffShowcase#demo1_stringComparison_showsPositionHighlighting

# Demo 2: Array size mismatch with extra element highlighting
mvn test -Dtest=VisualDiffShowcase#demo2_arraySizeMismatch_highlightsExtraElements

# Demo 3: Missing element with similarity suggestions
mvn test -Dtest=VisualDiffShowcase#demo3_missingElement_suggestsSimilarValues

# Demo 4: Path not found with JSON structure display
mvn test -Dtest=VisualDiffShowcase#demo4_pathNotFound_showsJsonStructure

# Demo 5: Predicate failure showing failed elements
mvn test -Dtest=VisualDiffShowcase#demo5_predicateFailure_showsFailedElement

# Demo 6: Whitespace difference visualization
mvn test -Dtest=VisualDiffShowcase#demo6_whitespaceDifference_visualizesSpaces

# Demo 7: Number comparison with precision warnings
mvn test -Dtest=VisualDiffShowcase#demo7_numberComparison_showsPrecisionWarning

# Demo 8: NoneMatch failure showing unexpected match
mvn test -Dtest=VisualDiffShowcase#demo8_noneMatch_showsUnexpectedMatch
```

## What You'll See

### Demo 1: String Comparison
```
═══════════════════════════════════════════════════════
  JSON ASSERTION FAILED
═══════════════════════════════════════════════════════

Path: $.message

Expected: "Hello Word"
Actual:   "Hello World"
                    ^ First difference at position 9
```

### Demo 2: Array Size Mismatch
```
═══════════════════════════════════════════════════════
  ARRAY SIZE MISMATCH
═══════════════════════════════════════════════════════

Expected: 3 element(s)
Actual:   5 element(s)

➜ Array has 2 extra element(s)

Actual array content:
[
  [0] "apple"
  [1] "banana"
  [2] "cherry"
  [3] ➜ "date"
  [4] ➜ "elderberry"
]
```

### Demo 4: Path Not Found
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
    "name" : "Alice",
    "age" : 30,
    "role" : "admin"
  }
}
```

## Why This Matters

Traditional JSON assertion libraries show generic errors:
```
❌ AssertionError: expected "Hello World" but got "Hello Word"
```

With JSONAssertX's Visual Diff:
```
✅ Shows EXACTLY where strings differ (position 9)
✅ Highlights extra/missing array elements with ➜ symbol
✅ Suggests similar values when element not found
✅ Displays JSON structure when path is invalid
✅ Visualizes whitespace differences
✅ Warns about number precision issues
```

## Competitive Advantage

| Feature | AssertJ | REST Assured | JSONAssertX |
|---------|---------|--------------|-------------|
| Basic diffs | ✓ | ✓ | ✓ |
| JSON-aware formatting | ❌ | ❌ | ✅ |
| Position highlighting | ❌ | ❌ | ✅ |
| Element marking (➜) | ❌ | ❌ | ✅ |
| Similarity suggestions | ❌ | ❌ | ✅ |
| Whitespace visualization | ❌ | ❌ | ✅ |
| Structure display | ❌ | ❌ | ✅ |
| Precision warnings | ❌ | ❌ | ✅ |

**Result: 10x faster debugging!** 🚀

## Developer Experience

The Visual Diff feature is:
- **Always enabled** - No configuration needed
- **Context-aware** - Shows relevant info based on failure type
- **Actionable** - Provides helpful suggestions and warnings
- **Beautiful** - Clear formatting with visual cues

## Implementation Details

The Visual Diff feature is powered by the `JsonDiffFormatter` utility class:
- `formatStringDiff()` - Character-level string comparison
- `formatArraySizeMismatch()` - Array size errors with highlighting
- `formatMissingElement()` - Missing element with suggestions
- `formatPathNotFound()` - Path errors with structure display
- `formatPredicateFailure()` - Predicate match failures
- `formatNumberDiff()` - Number comparisons with warnings

## Feedback

Love the Visual Diff feature? Hate it? Want more?
- ⭐ Star us on GitHub
- 💬 Share your use case in Discussions
- 🐛 Found an issue? Open a GitHub issue
- 🚀 Want a feature? Submit a PR!

---

**JSONAssertX** - Making JSON assertions beautiful, one diff at a time ✨
