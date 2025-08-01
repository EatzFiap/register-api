package com.eatz.application.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValuesConverterTest {

    @Nested
    @DisplayName("Convert Double to BigDecimal")
    class ToBigDecimal {

        @Test
        @DisplayName("Converts non-null Double to BigDecimal successfully")
        void convertsNonNullDoubleToBigDecimalSuccessfully() {
            Double value = 123.45;
            BigDecimal result = ValuesConverter.toBigDecimal(value);

            assertNotNull(result);
            assertEquals(BigDecimal.valueOf(value), result);
        }

        @Test
        @DisplayName("Returns null when converting null Double to BigDecimal")
        void returnsNullWhenConvertingNullDoubleToBigDecimal() {
            Double value = null;
            BigDecimal result = ValuesConverter.toBigDecimal(value);

            assertNull(result);
        }

        @Test
        @DisplayName("Throws exception when Double value is invalid for BigDecimal conversion")
        void throwsExceptionWhenDoubleValueIsInvalidForBigDecimalConversion() {
            Double value = Double.NaN;

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    ValuesConverter.toBigDecimal(value)
            );

            assertTrue(exception.getMessage().contains("Invalid value for conversion to BigDecimal"));
        }
    }

    @Nested
    @DisplayName("Convert BigDecimal to Double")
    class ToDouble {

        @Test
        @DisplayName("Converts non-null BigDecimal to Double successfully")
        void convertsNonNullBigDecimalToDoubleSuccessfully() {
            BigDecimal value = BigDecimal.valueOf(123.45);
            Double result = ValuesConverter.toDouble(value);

            assertNotNull(result);
            assertEquals(value.doubleValue(), result);
        }

        @Test
        @DisplayName("Returns null when converting null BigDecimal to Double")
        void returnsNullWhenConvertingNullBigDecimalToDouble() {
            BigDecimal value = null;
            Double result = ValuesConverter.toDouble(value);

            assertNull(result);
        }
        
    }

}