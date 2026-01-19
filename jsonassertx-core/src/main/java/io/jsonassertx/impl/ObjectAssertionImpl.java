package io.jsonassertx.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonassertx.ObjectAssertion;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of {@link ObjectAssertion}.
 * 
 * @since 1.0.0
 */
public class ObjectAssertionImpl extends JsonAssertionImpl implements ObjectAssertion {

    private final Set<String> keys;

    public ObjectAssertionImpl(String jsonString, JsonNode jsonNode, String currentPath, ObjectMapper objectMapper) {
        super(jsonString, jsonNode, currentPath, objectMapper);
        this.keys = new HashSet<>();
        Iterator<String> fieldNames = jsonNode.fieldNames();
        fieldNames.forEachRemaining(keys::add);
    }

    @Override
    public ObjectAssertion hasKey(String key) {
        if (!keys.contains(key)) {
            throw new AssertionError(
                String.format("Expected object to have key '%s' but it was not found%nAvailable keys: %s", 
                    key, keys)
            );
        }
        return this;
    }

    @Override
    public ObjectAssertion hasKeys(String... expectedKeys) {
        for (String key : expectedKeys) {
            if (!keys.contains(key)) {
                throw new AssertionError(
                    String.format("Expected object to have key '%s' but it was not found%nAvailable keys: %s", 
                        key, keys)
                );
            }
        }
        return this;
    }

    @Override
    public ObjectAssertion doesNotHaveKey(String key) {
        if (keys.contains(key)) {
            throw new AssertionError(
                String.format("Expected object to not have key '%s' but it was found", key)
            );
        }
        return this;
    }

    @Override
    public ObjectAssertion hasKeyCount(int count) {
        if (keys.size() != count) {
            throw new AssertionError(
                String.format("Expected object to have %d keys but it has %d%nKeys: %s", 
                    count, keys.size(), keys)
            );
        }
        return this;
    }
}
