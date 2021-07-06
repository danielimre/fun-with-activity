package com.funwithactivity.recommendationservice.activity.providera.client;

import java.math.BigDecimal;
import java.util.List;

import lombok.NonNull;
import lombok.Value;
import reactor.core.publisher.Mono;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ActivityProviderAApi {
    @POST("service1")
    Mono<List<ActivityRecommendationA>> recommend(@Body ActivityRecommendationARequest request);

    @Value
    final class ActivityRecommendationARequest {
        @NonNull
        private final BigDecimal height;
        @NonNull
        private final BigDecimal weight;
        @NonNull
        private final String token;
    }

    @Value
    final class ActivityRecommendationA {
        private final float confidence;
        @NonNull
        private final String recommendation;
    }
}
