package io.jsonassertx;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link NumberAssertion}.
 */
@DisplayName("Number Assertion Tests")
class NumberAssertionTest {

    @Test
    @DisplayName("Should validate number equals expected value")
    void shouldValidateNumberEquals() {
        String json = "{\"age\": 30}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.age").isNumber()
                .isEqualTo(30)
        );
    }

    @Test
    @DisplayName("Should fail when number does not equal expected")
    void shouldFailWhenNumberNotEqual() {
        String json = "{\"age\": 30}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.age").isNumber()
                .isEqualTo(25)
        );
    }

    @Test
    @DisplayName("Should validate number is greater than")
    void shouldValidateNumberGreaterThan() {
        String json = "{\"score\": 85}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.score").isNumber()
                .isGreaterThan(80)
        );
    }

    @Test
    @DisplayName("Should fail when number is not greater than")
    void shouldFailWhenNumberNotGreaterThan() {
        String json = "{\"score\": 75}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.score").isNumber()
                .isGreaterThan(80)
        );
    }

    @Test
    @DisplayName("Should validate number is greater than or equal to")
    void shouldValidateNumberGreaterThanOrEqual() {
        String json = "{\"age\": 18}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.age").isNumber()
                .isGreaterThanOrEqualTo(18)
        );
    }

    @Test
    @DisplayName("Should validate number is less than")
    void shouldValidateNumberLessThan() {
        String json = "{\"temperature\": 20}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.temperature").isNumber()
                .isLessThan(25)
        );
    }

    @Test
    @DisplayName("Should fail when number is not less than")
    void shouldFailWhenNumberNotLessThan() {
        String json = "{\"temperature\": 30}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.temperature").isNumber()
                .isLessThan(25)
        );
    }

    @Test
    @DisplayName("Should validate number is less than or equal to")
    void shouldValidateNumberLessThanOrEqual() {
        String json = "{\"count\": 10}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.count").isNumber()
                .isLessThanOrEqualTo(10)
        );
    }

    @Test
    @DisplayName("Should validate number is between range")
    void shouldValidateNumberBetween() {
        String json = "{\"age\": 30}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.age").isNumber()
                .isBetween(25, 35)
        );
    }

    @Test
    @DisplayName("Should fail when number is not in range")
    void shouldFailWhenNumberNotInRange() {
        String json = "{\"age\": 40}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.age").isNumber()
                .isBetween(25, 35)
        );
    }

    @Test
    @DisplayName("Should validate number is positive")
    void shouldValidateNumberIsPositive() {
        String json = "{\"balance\": 100.50}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.balance").isNumber()
                .isPositive()
        );
    }

    @Test
    @DisplayName("Should fail when number is not positive")
    void shouldFailWhenNumberNotPositive() {
        String json = "{\"balance\": -50}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.balance").isNumber()
                .isPositive()
        );
    }

    @Test
    @DisplayName("Should validate number is negative")
    void shouldValidateNumberIsNegative() {
        String json = "{\"debt\": -500}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.debt").isNumber()
                .isNegative()
        );
    }

    @Test
    @DisplayName("Should fail when number is not negative")
    void shouldFailWhenNumberNotNegative() {
        String json = "{\"debt\": 100}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.debt").isNumber()
                .isNegative()
        );
    }

    @Test
    @DisplayName("Should validate number is zero")
    void shouldValidateNumberIsZero() {
        String json = "{\"count\": 0}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.count").isNumber()
                .isZero()
        );
    }

    @Test
    @DisplayName("Should fail when number is not zero")
    void shouldFailWhenNumberNotZero() {
        String json = "{\"count\": 5}";
        
        assertThrows(AssertionError.class, () ->
            JsonAssertX.assertThat(json)
                .path("$.count").isNumber()
                .isZero()
        );
    }

    @Test
    @DisplayName("Should chain multiple number assertions")
    void shouldChainMultipleNumberAssertions() {
        String json = "{\"score\": 85}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.score").isNumber()
                .isGreaterThan(0)
                .isLessThan(100)
                .isBetween(80, 90)
                .isPositive()
        );
    }

    @Test
    @DisplayName("Should handle decimal numbers")
    void shouldHandleDecimalNumbers() {
        String json = "{\"price\": 19.99}";
        
        assertDoesNotThrow(() ->
            JsonAssertX.assertThat(json)
                .path("$.price").isNumber()
                .isEqualTo(19.99)
                .isGreaterThan(19)
                .isLessThan(20)
        );
    }
}
