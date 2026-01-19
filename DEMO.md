# JSONAssertX - Visual Diff Quick Demo

## 🚀 Quick Start

See the Visual Diff feature in action with one command:

```bash
# Run all 8 demo scenarios
cd jsonassertx-examples && mvn test -Dtest=VisualDiffShowcase
```

Or use the interactive runner:

**Windows:**
```powershell
.\run-visual-diff-demo.ps1
```

**Linux/Mac:**
```bash
./run-visual-diff-demo.sh
```

## 📖 What You'll See

The demos intentionally fail to showcase **8 different visual diff scenarios**:

1. **String Comparison** - Character-level diff with position marker (^)
2. **Array Size Mismatch** - Extra elements marked with ➜ symbol
3. **Missing Element** - Similarity suggestions with 💡 symbol
4. **Path Not Found** - JSON structure display with path breakdown
5. **Predicate Failure** - Shows which element failed (✗)
6. **Whitespace Detection** - Visualizes spaces as · symbol
7. **Number Comparison** - Precision warnings for small differences
8. **NoneMatch Failure** - Shows unexpected matches

## 💡 Example Output

### String Comparison
```
═══════════════════════════════════════════════════════
  JSON ASSERTION FAILED
═══════════════════════════════════════════════════════

Path: $.message

Expected: "Hello Word"
Actual:   "Hello World"
                    ^ First difference at position 9
```

### Array Size Mismatch
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

## ⚡ Why This Matters

**Traditional libraries** (AssertJ, REST Assured):
```
❌ AssertionError: expected "Hello World" but got "Hello Word"
```

**JSONAssertX with Visual Diff:**
```
✅ Shows EXACT position of difference (character 9)
✅ Highlights problematic elements
✅ Suggests similar values
✅ Displays JSON structure
✅ Warns about precision issues
```

**Result: 10x faster debugging!** 🎯

## 🎥 Run Individual Demos

```bash
cd jsonassertx-examples

# Demo 1: String comparison
mvn test -Dtest=VisualDiffShowcase#demo1_stringComparison_showsPositionHighlighting

# Demo 2: Array size mismatch
mvn test -Dtest=VisualDiffShowcase#demo2_arraySizeMismatch_highlightsExtraElements

# Demo 3: Missing element
mvn test -Dtest=VisualDiffShowcase#demo3_missingElement_suggestsSimilarValues

# Demo 4: Path not found
mvn test -Dtest=VisualDiffShowcase#demo4_pathNotFound_showsJsonStructure

# Demo 5: Predicate failure
mvn test -Dtest=VisualDiffShowcase#demo5_predicateFailure_showsFailedElement

# Demo 6: Whitespace detection
mvn test -Dtest=VisualDiffShowcase#demo6_whitespaceDifference_visualizesSpaces

# Demo 7: Number comparison
mvn test -Dtest=VisualDiffShowcase#demo7_numberComparison_showsPrecisionWarning

# Demo 8: NoneMatch failure
mvn test -Dtest=VisualDiffShowcase#demo8_noneMatch_showsUnexpectedMatch
```

## 📚 Learn More

- **Detailed Documentation**: `docs/visual-diff-feature.md`
- **API Reference**: `docs/api-reference.md`
- **Getting Started**: `docs/getting-started.md`
- **Example Code**: `jsonassertx-examples/src/test/java/.../VisualDiffShowcase.java`

## 🌟 Competitive Advantage

| Feature | AssertJ | REST Assured | **JSONAssertX** |
|---------|---------|--------------|-----------------|
| JSON-aware formatting | ❌ | ❌ | ✅ |
| Position highlighting | ❌ | ❌ | ✅ |
| Element marking (➜) | ❌ | ❌ | ✅ |
| Similarity suggestions (💡) | ❌ | ❌ | ✅ |
| Whitespace visualization | ❌ | ❌ | ✅ |
| Structure display | ❌ | ❌ | ✅ |
| Precision warnings | ❌ | ❌ | ✅ |

---

**JSONAssertX** - Making JSON assertions beautiful ✨
