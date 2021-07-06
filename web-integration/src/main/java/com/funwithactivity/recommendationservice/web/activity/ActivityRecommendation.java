package com.funwithactivity.recommendationservice.web.activity;

import java.util.List;

import lombok.NonNull;
import lombok.Value;

@Value
public final class ActivityRecommendation {
    @NonNull
    private final List<String> activities;
}
