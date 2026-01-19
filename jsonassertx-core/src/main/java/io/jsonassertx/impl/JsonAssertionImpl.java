package io.jsonassertx.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.jsonassertx.*;
import io.jsonassertx.util.JsonDiffFormatter;

/**
 * Base implementation of {@link JsonAssertion}.
 * 
 * @since 1.0.0
 */
public class JsonAssertionImpl implements JsonAssertion {

    protected final String jsonString;
    protected final JsonNode jsonNode;
    protected final String currentPath;
    protected final ObjectMapper objectMapper;

    public JsonAssertionImpl(String json) {
        try {
            this.jsonString = json;
            this.objectMapper = new ObjectMapper();
            this.jsonNode = objectMapper.readTree(json);
            this.currentPath = "$";
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON: " + e.getMessage(), e);
        }
    }

    public JsonAssertionImpl(Object jsonObject) {
        try {
            this.objectMapper = new ObjectMapper();
            this.jsonString = objectMapper.writeValueAsString(jsonObject);
            this.jsonNode = objectMapper.valueToTree(jsonObject);
            this.currentPath = "$";
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot serialize object to JSON: " + e.getMessage(), e);
        }
    }

    protected JsonAssertionImpl(String jsonString, JsonNode jsonNode, String currentPath, ObjectMapper objectMapper) {
        this.jsonString = jsonString;
        this.jsonNode = jsonNode;
        this.currentPath = currentPath;
        this.objectMapper = objectMapper;
    }

    @Override
    public JsonAssertion hasPath(String path) {
        try {
            JsonPath.read(jsonString, path);
            return this;
        } catch (Exception e) {
            throw new AssertionError(
                JsonDiffFormatter.formatPathNotFound(path, jsonString)
            );
        }
    }

    @Override
    public JsonAssertion doesNotHavePath(String path) {
        try {
            JsonPath.read(jsonString, path);
            throw new AssertionError(
                String.format("Expected JSON to not have path '%s' but it was found%n%nJSON:%n%s", 
                    path, formatJson())
            );
        } catch (com.jayway.jsonpath.PathNotFoundException e) {
            return this;
        }
    }

    @Override
    public JsonAssertion hasValue(String path, Object expected) {
        try {
            Object actual = JsonPath.read(jsonString, path);
            if (expected == null && actual == null) {
                return this;
            }
            if (expected != null && expected.equals(actual)) {
                return this;
            }
            if (expected instanceof Number && actual instanceof Number && 
                compareNumbers((Number) expected, (Number) actual)) {
                return this;
            }
            
            throw new AssertionError(
                String.format("JSON assertion failed at path: %s%n%nExpected: %s%nActual:   %s%n%nJSON:%n%s",
                    path, expected, actual, formatJson())
            );
        } catch (com.jayway.jsonpath.PathNotFoundException e) {
            throw new AssertionError(
                String.format("Path '%s' not found in JSON%n%nJSON:%n%s", path, formatJson())
            );
        }
    }

    @Override
    public JsonAssertion isNotNull() {
        if (jsonNode == null || jsonNode.isNull()) {
            throw new AssertionError("Expected JSON to not be null but it was null");
        }
        return this;
    }

    @Override
    public JsonAssertion isNull() {
        if (jsonNode != null && !jsonNode.isNull()) {
            throw new AssertionError(
                String.format("Expected JSON to be null but it was:%n%s", formatJson())
            );
        }
        return this;
    }

    @Override
    public JsonAssertion isNotEmpty() {
        if (jsonNode == null) {
            throw new AssertionError("Expected JSON to not be empty but it was null");
        }
        
        if (jsonNode.isObject() && jsonNode.size() == 0) {
            throw new AssertionError("Expected JSON object to not be empty but it was {}");
        }
        if (jsonNode.isArray() && jsonNode.size() == 0) {
            throw new AssertionError("Expected JSON array to not be empty but it was []");
        }
        if (jsonNode.isTextual() && jsonNode.asText().isEmpty()) {
            throw new AssertionError("Expected JSON string to not be empty but it was \"\"");
        }
        
        return this;
    }

    @Override
    public JsonAssertion isEmpty() {
        if (jsonNode == null) {
            return this; // null is considered empty
        }
        
        boolean empty = ((jsonNode.isObject() || jsonNode.isArray()) && jsonNode.size() == 0) ||
                        (jsonNode.isTextual() && jsonNode.asText().isEmpty());
        
        if (!empty) {
            throw new AssertionError(
                String.format("Expected JSON to be empty but it was:%n%s", formatJson())
            );
        }
        
        return this;
    }

    @Override
    public ObjectAssertion isObject() {
        if (!jsonNode.isObject()) {
            throw new AssertionError(
                String.format("Expected JSON to be an object but it was: %s", jsonNode.getNodeType())
            );
        }
        return new ObjectAssertionImpl(jsonString, jsonNode, currentPath, objectMapper);
    }

    @Override
    public ArrayAssertion isArray() {
        if (!jsonNode.isArray()) {
            throw new AssertionError(
                String.format("Expected JSON to be an array but it was: %s", jsonNode.getNodeType())
            );
        }
        return new ArrayAssertionImpl(jsonString, jsonNode, currentPath, objectMapper);
    }

    @Override
    public StringAssertion isString() {
        if (!jsonNode.isTextual()) {
            throw new AssertionError(
                String.format("Expected JSON to be a string but it was: %s", jsonNode.getNodeType())
            );
        }
        return new StringAssertionImpl(jsonString, jsonNode, currentPath, objectMapper);
    }

    @Override
    public NumberAssertion isNumber() {
        if (!jsonNode.isNumber()) {
            throw new AssertionError(
                String.format("Expected JSON to be a number but it was: %s", jsonNode.getNodeType())
            );
        }
        return new NumberAssertionImpl(jsonString, jsonNode, currentPath, objectMapper);
    }

    @Override
    public BooleanAssertion isBoolean() {
        if (!jsonNode.isBoolean()) {
            throw new AssertionError(
                String.format("Expected JSON to be a boolean but it was: %s", jsonNode.getNodeType())
            );
        }
        return new BooleanAssertionImpl(jsonString, jsonNode, currentPath, objectMapper);
    }

    @Override
    public JsonAssertion path(String path) {
        try {
            Object value = JsonPath.read(jsonString, path);
            JsonNode node = objectMapper.valueToTree(value);
            return new JsonAssertionImpl(jsonString, node, path, objectMapper);
        } catch (Exception e) {
            throw new AssertionError(
                String.format("Path '%s' not found in JSON%n%nJSON:%n%s", path, formatJson())
            );
        }
    }

    protected String formatJson() {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            return jsonString;
        }
    }

    protected boolean compareNumbers(Number expected, Number actual) {
        return Double.compare(expected.doubleValue(), actual.doubleValue()) == 0;
    }
}
