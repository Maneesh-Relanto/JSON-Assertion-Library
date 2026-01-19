package io.jsonassertx.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonassertx.ArrayAssertion;
import io.jsonassertx.JsonAssertion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Implementation of {@link ArrayAssertion}.
 * 
 * @since 1.0.0
 */
public class ArrayAssertionImpl extends JsonAssertionImpl implements ArrayAssertion {

    private final List<Object> elements;

    public ArrayAssertionImpl(String jsonString, JsonNode jsonNode, String currentPath, ObjectMapper objectMapper) {
        super(jsonString, jsonNode, currentPath, objectMapper);
        this.elements = new ArrayList<>();
        jsonNode.forEach(node -> elements.add(convertNode(node)));
    }

    @Override
    public ArrayAssertion hasSize(int size) {
        if (jsonNode.size() != size) {
            throw new AssertionError(
                String.format("Expected array size to be %d but was %d", size, jsonNode.size())
            );
        }
        return this;
    }

    @Override
    public ArrayAssertion contains(Object element) {
        if (!elements.contains(element)) {
            throw new AssertionError(
                String.format("Expected array to contain %s but it was not found%nArray: %s", 
                    element, elements)
            );
        }
        return this;
    }

    @Override
    public ArrayAssertion containsAll(Object... expectedElements) {
        for (Object element : expectedElements) {
            if (!elements.contains(element)) {
                throw new AssertionError(
                    String.format("Expected array to contain %s but it was not found%nArray: %s", 
                        element, elements)
                );
            }
        }
        return this;
    }

    @Override
    public ArrayAssertion doesNotContain(Object element) {
        if (elements.contains(element)) {
            throw new AssertionError(
                String.format("Expected array to not contain %s but it was found%nArray: %s", 
                    element, elements)
            );
        }
        return this;
    }

    @Override
    public ArrayAssertion allMatch(Predicate<Object> predicate) {
        for (int i = 0; i < elements.size(); i++) {
            if (!predicate.test(elements.get(i))) {
                throw new AssertionError(
                    String.format("Expected all elements to match predicate but element at index %d did not: %s", 
                        i, elements.get(i))
                );
            }
        }
        return this;
    }

    @Override
    public ArrayAssertion anyMatch(Predicate<Object> predicate) {
        for (Object element : elements) {
            if (predicate.test(element)) {
                return this;
            }
        }
        throw new AssertionError(
            String.format("Expected at least one element to match predicate but none did%nArray: %s", elements)
        );
    }

    @Override
    public ArrayAssertion noneMatch(Predicate<Object> predicate) {
        for (int i = 0; i < elements.size(); i++) {
            if (predicate.test(elements.get(i))) {
                throw new AssertionError(
                    String.format("Expected no elements to match predicate but element at index %d did: %s", 
                        i, elements.get(i))
                );
            }
        }
        return this;
    }

    @Override
    public JsonAssertion element(int index) {
        if (index < 0 || index >= jsonNode.size()) {
            throw new AssertionError(
                String.format("Array index %d is out of bounds (size: %d)", index, jsonNode.size())
            );
        }
        JsonNode elementNode = jsonNode.get(index);
        String elementPath = currentPath + "[" + index + "]";
        return new JsonAssertionImpl(jsonString, elementNode, elementPath, objectMapper);
    }

    private Object convertNode(JsonNode node) {
        if (node.isTextual()) {
            return node.asText();
        } else if (node.isNumber()) {
            return node.numberValue();
        } else if (node.isBoolean()) {
            return node.asBoolean();
        } else if (node.isNull()) {
            return null;
        } else {
            return node;
        }
    }
}
