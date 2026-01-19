package io.jsonassertx.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonassertx.NumberAssertion;

/**
 * Implementation of {@link NumberAssertion}.
 * 
 * @since 1.0.0
 */
public class NumberAssertionImpl extends JsonAssertionImpl implements NumberAssertion {

    private final double value;

    public NumberAssertionImpl(String jsonString, JsonNode jsonNode, String currentPath, ObjectMapper objectMapper) {
        super(jsonString, jsonNode, currentPath, objectMapper);
        this.value = jsonNode.asDouble();
    }

    @Override
    public NumberAssertion isEqualTo(Number expected) {
        if (Double.compare(value, expected.doubleValue()) != 0) {
            throw new AssertionError(
                String.format("Expected number to equal %s but was %s", expected, value)
            );
        }
        return this;
    }

    @Override
    public NumberAssertion isGreaterThan(Number other) {
        if (value <= other.doubleValue()) {
            throw new AssertionError(
                String.format("Expected %s to be greater than %s", value, other)
            );
        }
        return this;
    }

    @Override
    public NumberAssertion isGreaterThanOrEqualTo(Number other) {
        if (value < other.doubleValue()) {
            throw new AssertionError(
                String.format("Expected %s to be greater than or equal to %s", value, other)
            );
        }
        return this;
    }

    @Override
    public NumberAssertion isLessThan(Number other) {
        if (value >= other.doubleValue()) {
            throw new AssertionError(
                String.format("Expected %s to be less than %s", value, other)
            );
        }
        return this;
    }

    @Override
    public NumberAssertion isLessThanOrEqualTo(Number other) {
        if (value > other.doubleValue()) {
            throw new AssertionError(
                String.format("Expected %s to be less than or equal to %s", value, other)
            );
        }
        return this;
    }

    @Override
    public NumberAssertion isBetween(Number start, Number end) {
        double startValue = start.doubleValue();
        double endValue = end.doubleValue();
        
        if (value < startValue || value > endValue) {
            throw new AssertionError(
                String.format("Expected %s to be between %s and %s (inclusive)", value, start, end)
            );
        }
        return this;
    }

    @Override
    public NumberAssertion isPositive() {
        if (value <= 0) {
            throw new AssertionError(
                String.format("Expected %s to be positive", value)
            );
        }
        return this;
    }

    @Override
    public NumberAssertion isNegative() {
        if (value >= 0) {
            throw new AssertionError(
                String.format("Expected %s to be negative", value)
            );
        }
        return this;
    }

    @Override
    public NumberAssertion isZero() {
        if (Double.compare(value, 0.0) != 0) {
            throw new AssertionError(
                String.format("Expected number to be zero but was %s", value)
            );
        }
        return this;
    }
}
