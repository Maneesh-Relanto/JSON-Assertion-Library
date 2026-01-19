package io.jsonassertx.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonassertx.StringAssertion;

import java.util.regex.Pattern;

/**
 * Implementation of {@link StringAssertion}.
 * 
 * @since 1.0.0
 */
public class StringAssertionImpl extends JsonAssertionImpl implements StringAssertion {

    private final String value;

    public StringAssertionImpl(String jsonString, JsonNode jsonNode, String currentPath, ObjectMapper objectMapper) {
        super(jsonString, jsonNode, currentPath, objectMapper);
        this.value = jsonNode.asText();
    }

    @Override
    public StringAssertion isEqualTo(String expected) {
        if (!value.equals(expected)) {
            throw new AssertionError(
                String.format("Expected string to equal '%s' but was '%s'", expected, value)
            );
        }
        return this;
    }

    @Override
    public StringAssertion contains(String substring) {
        if (!value.contains(substring)) {
            throw new AssertionError(
                String.format("Expected string '%s' to contain '%s'", value, substring)
            );
        }
        return this;
    }

    @Override
    public StringAssertion startsWith(String prefix) {
        if (!value.startsWith(prefix)) {
            throw new AssertionError(
                String.format("Expected string '%s' to start with '%s'", value, prefix)
            );
        }
        return this;
    }

    @Override
    public StringAssertion endsWith(String suffix) {
        if (!value.endsWith(suffix)) {
            throw new AssertionError(
                String.format("Expected string '%s' to end with '%s'", value, suffix)
            );
        }
        return this;
    }

    @Override
    public StringAssertion matches(String regex) {
        if (!Pattern.matches(regex, value)) {
            throw new AssertionError(
                String.format("Expected string '%s' to match regex '%s'", value, regex)
            );
        }
        return this;
    }

    @Override
    public StringAssertion hasLength(int length) {
        if (value.length() != length) {
            throw new AssertionError(
                String.format("Expected string length to be %d but was %d (string: '%s')", 
                    length, value.length(), value)
            );
        }
        return this;
    }
}
