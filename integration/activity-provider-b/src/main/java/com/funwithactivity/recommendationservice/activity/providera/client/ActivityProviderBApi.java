package com.funwithactivity.recommendationservice.activity.providera.client;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import reactor.core.publisher.Mono;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ActivityProviderBApi {
    @POST("service2")
    Mono<ActivityRecommendationsB> recommend(@Body ActivityRecommendationBRequest request);

    @Value
    @Builder
    final class ActivityRecommendationBRequest {
        @NonNull
        private final Measurements measurements;
        private final long birthDate;
        @NonNull
        private final String session;
    }

    @Value
    final class Measurements {
        @NonNull
        private final BigDecimal mass;
        @NonNull
        private final BigDecimal height;
    }

    @Value
    final class ActivityRecommendationsB {
        @NonNull
        private final List<ActivityRecommendationB> recommendations;
    }

    @Value
    final class ActivityRecommendationB {
        private final int priority;
        @NonNull
        private final String title;
        @NonNull
        private final String details;
    }
}
