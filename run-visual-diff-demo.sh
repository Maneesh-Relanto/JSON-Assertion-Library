#!/usr/bin/env bash

# Visual Diff Demo Runner
# Run this script to see JSONAssertX's Visual Diff feature in action!

echo "╔═══════════════════════════════════════════════════════════════╗"
echo "║                                                               ║"
echo "║  JSONAssertX - Visual Diff Feature Demo                      ║"
echo "║                                                               ║"
echo "╚═══════════════════════════════════════════════════════════════╝"
echo ""
echo "This demo will show you 8 different visual diff scenarios."
echo "All tests will intentionally fail to demonstrate the enhanced"
echo "error messages that make debugging 10x faster!"
echo ""
echo "Press Enter to start..."
read

cd "$(dirname "$0")/jsonassertx-examples"

demos=(
    "demo1_stringComparison_showsPositionHighlighting:String Comparison"
    "demo2_arraySizeMismatch_highlightsExtraElements:Array Size Mismatch"
    "demo3_missingElement_suggestsSimilarValues:Missing Element Suggestions"
    "demo4_pathNotFound_showsJsonStructure:Path Not Found"
    "demo5_predicateFailure_showsFailedElement:Predicate Failure"
    "demo6_whitespaceDifference_visualizesSpaces:Whitespace Detection"
    "demo7_numberComparison_showsPrecisionWarning:Number Comparison"
    "demo8_noneMatch_showsUnexpectedMatch:NoneMatch Failure"
)

for demo in "${demos[@]}"; do
    IFS=':' read -r method title <<< "$demo"
    
    echo ""
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "  $title"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo ""
    
    mvn test -Dtest="VisualDiffShowcase#$method" -q 2>&1 | \
        grep -A 20 "JSON ASSERTION FAILED\|ARRAY SIZE MISMATCH\|ELEMENT NOT FOUND\|PATH NOT FOUND\|PREDICATE MATCH FAILED" | \
        head -n 25
    
    echo ""
    echo "Press Enter to continue to next demo..."
    read
done

echo ""
echo "╔═══════════════════════════════════════════════════════════════╗"
echo "║                                                               ║"
echo "║  Demo Complete! ✨                                            ║"
echo "║                                                               ║"
echo "╚═══════════════════════════════════════════════════════════════╝"
echo ""
echo "What you just saw:"
echo "  ✓ Character-level string comparison"
echo "  ✓ Array element highlighting with ➜ symbol"
echo "  ✓ Similarity suggestions with 💡 symbol"
echo "  ✓ JSON structure display for invalid paths"
echo "  ✓ Predicate failure context"
echo "  ✓ Whitespace visualization with · symbol"
echo "  ✓ Number precision warnings"
echo "  ✓ Clear visual separators (═══)"
echo ""
echo "This is what makes JSONAssertX different from:"
echo "  • AssertJ (basic diffs, no JSON-awareness)"
echo "  • REST Assured (verbose output, hard to parse)"
echo ""
echo "Result: 10x faster debugging! 🚀"
echo ""
echo "Learn more:"
echo "  • README: ./jsonassertx-examples/README.md"
echo "  • Docs: ./docs/visual-diff-feature.md"
echo "  • Tests: ./jsonassertx-examples/src/test/java/.../VisualDiffShowcase.java"
echo ""
