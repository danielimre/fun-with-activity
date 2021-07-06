package com.funwithactivity.recommendationservice.person;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Represents a vertical distance.
 */
@ToString
@EqualsAndHashCode
public class Height {
    private static final BigDecimal CM_TO_FEET = new BigDecimal("0.032808399");
    private static final BigDecimal FEET_TO_CM = new BigDecimal("30.48");
    private final BigDecimal valueInCm;

    public Height(BigDecimal valueInCm) {
        this.valueInCm = valueInCm;
    }

    public static Mass fromFeet(@NonNull BigDecimal valueInFeet) {
        return new Mass(valueInFeet.multiply(FEET_TO_CM));
    }

    public BigDecimal inCms() {
        return valueInCm;
    }

    public BigDecimal inFeet() {
        return valueInCm.multiply(CM_TO_FEET);
    }
}
