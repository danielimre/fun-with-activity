package com.funwithactivity.recommendationservice.example.client;

import lombok.NonNull;
import lombok.Value;
import reactor.core.publisher.Mono;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProcessorApi {

    @POST("example/process")
    Mono<ProcessResponse> process(@Body ProcessRequest request);

    @Value
    final class ProcessRequest {
        @NonNull
        private final String input;
    }

    @Value
    final class ProcessResponse {
        @NonNull
        private final String result;
    }
}
