package com.funwithactivity.recommendationservice.person;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Represents mass of an entity.
 */
@ToString
@EqualsAndHashCode
public final class Mass {
    private static final BigDecimal KG_TO_POUND = new BigDecimal("2.20462262");
    private static final BigDecimal POUND_TO_KG = new BigDecimal("0.45359237");
    private final BigDecimal valueInKg;

    public Mass(BigDecimal valueInKg) {
        this.valueInKg = valueInKg;
    }

    public static Mass fromPounds(@NonNull BigDecimal valueInLb) {
        return new Mass(valueInLb.multiply(POUND_TO_KG));
    }

    public BigDecimal inKgs() {
        return valueInKg;
    }

    public BigDecimal inPounds() {
        return valueInKg.multiply(KG_TO_POUND);
    }
}
