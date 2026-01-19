# Contributing to JSONAssertX

Thank you for considering contributing to JSONAssertX! We welcome contributions from the community.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How Can I Contribute?](#how-can-i-contribute)
- [Development Setup](#development-setup)
- [Pull Request Process](#pull-request-process)
- [Coding Guidelines](#coding-guidelines)
- [Testing Guidelines](#testing-guidelines)
- [Commit Message Guidelines](#commit-message-guidelines)

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code. Please report unacceptable behavior to the project maintainers.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the existing issues to avoid duplicates. When creating a bug report, include:

- **Clear title and description** of the issue
- **Steps to reproduce** the behavior
- **Expected behavior** vs actual behavior
- **Code samples** demonstrating the issue
- **Environment details** (Java version, library version, OS)

### Suggesting Enhancements

Enhancement suggestions are welcome! Please provide:

- **Clear use case** for the enhancement
- **Examples** of how the API would be used
- **Comparison** with existing functionality
- **Alternative approaches** you've considered

### Pull Requests

We actively welcome your pull requests:

1. Fork the repo and create your branch from `main`
2. If you've added code, add tests
3. If you've changed APIs, update the documentation
4. Ensure the test suite passes
5. Make sure your code follows the coding guidelines
6. Issue the pull request

## Development Setup

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Git

### Building the Project

```bash
# Clone the repository
git clone https://github.com/jsonassertx/jsonassertx.git
cd jsonassertx

# Build all modules
mvn clean install

# Build without running tests
mvn clean install -DskipTests

# Run tests only
mvn test
```

### Project Structure

```
jsonassertx/
├── jsonassertx-core/          # Core assertion library
├── jsonassertx-junit5/        # JUnit 5 integration
├── jsonassertx-schema/        # JSON Schema validation
├── jsonassertx-snapshots/     # Snapshot testing
├── jsonassertx-spring/        # Spring integration
├── jsonassertx-codegen/       # Code generation tools
└── jsonassertx-examples/      # Usage examples
```

### Running Tests

```bash
# Run all tests
mvn test

# Run tests for specific module
mvn test -pl jsonassertx-core

# Run with coverage
mvn clean test jacoco:report

# Run integration tests
mvn verify
```

## Pull Request Process

1. **Update documentation** for any changed functionality
2. **Add tests** that cover your changes
3. **Update the changelog** if applicable
4. **Ensure all tests pass** locally before submitting
5. **Follow the commit message guidelines** below
6. **Request review** from maintainers
7. **Address review feedback** promptly

### PR Checklist

- [ ] Code compiles without errors
- [ ] All tests pass
- [ ] New tests added for new functionality
- [ ] Code coverage maintained or improved
- [ ] Documentation updated
- [ ] No unnecessary dependencies added
- [ ] Code follows project style guidelines
- [ ] Commit messages follow guidelines

## Coding Guidelines

### Java Style

- Follow standard Java naming conventions
- Use **4 spaces** for indentation (no tabs)
- Maximum line length: **120 characters**
- Use **meaningful variable names**
- Add **Javadoc** for public APIs

### Code Quality

```java
// Good: Fluent, readable API
assertThatJson(response)
    .hasPath("$.user.name")
    .hasValue("$.user.age", 25)
    .isNotEmpty();

// Bad: Unclear, complex logic
if (json.get("user") != null && json.get("user").get("name") != null) {
    // ...
}
```

### Best Practices

- **Single Responsibility**: Each class should have one clear purpose
- **Immutability**: Prefer immutable objects where possible
- **Null Safety**: Use `Optional` instead of returning null
- **Error Messages**: Provide clear, actionable error messages
- **Fluent APIs**: Chain methods for readable assertions

## Testing Guidelines

### Test Structure

```java
@Test
@DisplayName("Should validate JSON path exists")
void shouldValidateJsonPathExists() {
    // Given
    String json = "{\"user\": {\"name\": \"John\"}}";
    
    // When & Then
    assertThatJson(json)
        .hasPath("$.user.name")
        .hasValue("$.user.name", "John");
}
```

### Test Coverage

- Maintain **minimum 80% code coverage**
- Test **happy paths** and **error cases**
- Include **edge cases** and **boundary conditions**
- Test **null/empty** inputs
- Add **integration tests** for complex scenarios

### Test Naming

- Use descriptive test names: `shouldValidateWhenJsonPathExists()`
- Use `@DisplayName` for readable test descriptions
- Group related tests with `@Nested` classes

## Commit Message Guidelines

### Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

- **feat**: New feature
- **fix**: Bug fix
- **docs**: Documentation changes
- **style**: Code style changes (formatting)
- **refactor**: Code refactoring
- **test**: Adding or updating tests
- **chore**: Maintenance tasks

### Examples

```
feat(core): add support for custom JSON comparators

Implement custom comparator interface that allows users to define
their own comparison logic for specific JSON paths.

Closes #123
```

```
fix(junit5): resolve NPE in assertion error message

Fixed NullPointerException when generating error messages for
null JSON values in JUnit 5 assertions.

Fixes #456
```

### Scope Values

- `core` - Core library
- `junit5` - JUnit 5 integration
- `schema` - Schema validation
- `snapshots` - Snapshot testing
- `spring` - Spring integration
- `codegen` - Code generation
- `examples` - Examples
- `docs` - Documentation

## Documentation

### Javadoc Requirements

```java
/**
 * Asserts that the JSON contains the specified path.
 *
 * @param path the JSON path to check (using JsonPath notation)
 * @return this assertion object for method chaining
 * @throws AssertionError if the path does not exist
 * @throws IllegalArgumentException if the path is null or invalid
 */
public JsonAssertion hasPath(String path) {
    // implementation
}
```

### README Updates

- Update README.md for new features
- Include code examples
- Update table of contents if needed

## Questions?

If you have questions about contributing:

- Open an issue with the `question` label
- Check existing documentation
- Reach out to maintainers

## License

By contributing to JSONAssertX, you agree that your contributions will be licensed under the Apache License 2.0.

---

Thank you for contributing to JSONAssertX! 🎉
