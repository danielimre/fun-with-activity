package com.funwithactivity.recommendationservice.web;

import lombok.NonNull;
import lombok.Value;

@Value
public class ErrorResponse {
    @NonNull
    private final String timestamp;
    private final int status;
    @NonNull
    private final String error;
    @NonNull
    private final String path;
}
