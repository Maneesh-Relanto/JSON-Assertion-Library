package io.jsonassertx.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonassertx.BooleanAssertion;

/**
 * Implementation of {@link BooleanAssertion}.
 * 
 * @since 1.0.0
 */
public class BooleanAssertionImpl extends JsonAssertionImpl implements BooleanAssertion {

    private final boolean value;

    public BooleanAssertionImpl(String jsonString, JsonNode jsonNode, String currentPath, ObjectMapper objectMapper) {
        super(jsonString, jsonNode, currentPath, objectMapper);
        this.value = jsonNode.asBoolean();
    }

    @Override
    public BooleanAssertion isTrue() {
        if (!value) {
            throw new AssertionError("Expected boolean to be true but was false");
        }
        return this;
    }

    @Override
    public BooleanAssertion isFalse() {
        if (value) {
            throw new AssertionError("Expected boolean to be false but was true");
        }
        return this;
    }
}
