package com.funwithactivity.recommendationservice.activity;

import java.util.Optional;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public final class Activity {
    private final int priority;
    @NonNull
    private final String summary;
    private final String details;

    public Optional<String> getDetails() {
        return Optional.ofNullable(details);
    }
}
