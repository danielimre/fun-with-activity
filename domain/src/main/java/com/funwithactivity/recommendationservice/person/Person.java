package com.funwithactivity.recommendationservice.person;

import java.time.LocalDate;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;


@Value
@Builder
public class Person {
    @NonNull
    private final Height height;
    @NonNull
    private final Mass weight;
    @NonNull
    private final LocalDate birthDate;
}
