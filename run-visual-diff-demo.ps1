# Visual Diff Demo Runner (Windows PowerShell)
# Run this script to see JSONAssertX's Visual Diff feature in action!

Write-Host "╔═══════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║                                                               ║" -ForegroundColor Cyan
Write-Host "║  JSONAssertX - Visual Diff Feature Demo                      ║" -ForegroundColor Cyan
Write-Host "║                                                               ║" -ForegroundColor Cyan
Write-Host "╚═══════════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""
Write-Host "This demo will show you 8 different visual diff scenarios." -ForegroundColor Yellow
Write-Host "All tests will intentionally fail to demonstrate the enhanced" -ForegroundColor Yellow
Write-Host "error messages that make debugging 10x faster!" -ForegroundColor Yellow
Write-Host ""
Write-Host "Press Enter to start..." -ForegroundColor Green
$null = Read-Host

$demos = @(
    @{Method="demo1_stringComparison_showsPositionHighlighting"; Title="String Comparison"},
    @{Method="demo2_arraySizeMismatch_highlightsExtraElements"; Title="Array Size Mismatch"},
    @{Method="demo3_missingElement_suggestsSimilarValues"; Title="Missing Element Suggestions"},
    @{Method="demo4_pathNotFound_showsJsonStructure"; Title="Path Not Found"},
    @{Method="demo5_predicateFailure_showsFailedElement"; Title="Predicate Failure"},
    @{Method="demo6_whitespaceDifference_visualizesSpaces"; Title="Whitespace Detection"},
    @{Method="demo7_numberComparison_showsPrecisionWarning"; Title="Number Comparison"},
    @{Method="demo8_noneMatch_showsUnexpectedMatch"; Title="NoneMatch Failure"}
)

Set-Location "$PSScriptRoot\jsonassertx-examples"

foreach ($demo in $demos) {
    Write-Host ""
    Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Magenta
    Write-Host "  $($demo.Title)" -ForegroundColor Magenta
    Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Magenta
    Write-Host ""
    
    $output = mvn test "-Dtest=VisualDiffShowcase#$($demo.Method)" -q 2>&1 | Out-String
    $lines = $output -split "`n"
    
    $startPrinting = $false
    $lineCount = 0
    foreach ($line in $lines) {
        if ($line -match "FAILED|MISMATCH|NOT FOUND|═══") {
            $startPrinting = $true
        }
        if ($startPrinting -and $lineCount -lt 25) {
            Write-Host $line
            $lineCount++
        }
    }
    
    Write-Host ""
    Write-Host "Press Enter to continue to next demo..." -ForegroundColor Green
    $null = Read-Host
}

Write-Host ""
Write-Host "╔═══════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║                                                               ║" -ForegroundColor Cyan
Write-Host "║  Demo Complete! ✨                                            ║" -ForegroundColor Cyan
Write-Host "║                                                               ║" -ForegroundColor Cyan
Write-Host "╚═══════════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""
Write-Host "What you just saw:" -ForegroundColor Yellow
Write-Host "  ✓ Character-level string comparison" -ForegroundColor Green
Write-Host "  ✓ Array element highlighting with ➜ symbol" -ForegroundColor Green
Write-Host "  ✓ Similarity suggestions with 💡 symbol" -ForegroundColor Green
Write-Host "  ✓ JSON structure display for invalid paths" -ForegroundColor Green
Write-Host "  ✓ Predicate failure context" -ForegroundColor Green
Write-Host "  ✓ Whitespace visualization with · symbol" -ForegroundColor Green
Write-Host "  ✓ Number precision warnings" -ForegroundColor Green
Write-Host "  ✓ Clear visual separators (═══)" -ForegroundColor Green
Write-Host ""
Write-Host "This is what makes JSONAssertX different from:" -ForegroundColor Yellow
Write-Host "  • AssertJ (basic diffs, no JSON-awareness)" -ForegroundColor White
Write-Host "  • REST Assured (verbose output, hard to parse)" -ForegroundColor White
Write-Host ""
Write-Host "Result: 10x faster debugging! 🚀" -ForegroundColor Cyan
Write-Host ""
Write-Host "Learn more:" -ForegroundColor Yellow
Write-Host "  • README: .\jsonassertx-examples\README.md" -ForegroundColor White
Write-Host "  • Docs: .\docs\visual-diff-feature.md" -ForegroundColor White
Write-Host "  • Tests: .\jsonassertx-examples\src\test\java\...\VisualDiffShowcase.java" -ForegroundColor White
Write-Host ""

Set-Location $PSScriptRoot
