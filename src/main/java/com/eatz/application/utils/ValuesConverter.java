package com.eatz.application.utils;

import java.math.BigDecimal;

public class ValuesConverter {

    private ValuesConverter() {
    }

    public static BigDecimal toBigDecimal(Double value) {
        if (value == null) {
            return null;
        }
        try {
            return BigDecimal.valueOf(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid value for conversion to BigDecimal: " + value, e);
        }
    }

    public static Double toDouble(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return value.doubleValue();
    }
}
