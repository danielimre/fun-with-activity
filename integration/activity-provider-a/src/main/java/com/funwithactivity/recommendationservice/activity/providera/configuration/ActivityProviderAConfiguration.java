package com.funwithactivity.recommendationservice.activity.providera.configuration;

import com.funwithactivity.recommendationservice.activity.ActivityRecommendationProvider;
import com.funwithactivity.recommendationservice.activity.providera.RemoteActivityRecommendationProviderA;
import com.funwithactivity.recommendationservice.activity.providera.client.ActivityProviderAApi;
import com.hotels.molten.http.client.RetrofitServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivityProviderAConfiguration {
    @Value("${integration.activityProviderA.client.baseUrl}")
    private String baseUrl;

    @Bean
    public ActivityRecommendationProvider activityRecommendationProviderA() {
        return new RemoteActivityRecommendationProviderA(client());
    }

    private ActivityProviderAApi client() {
        return RetrofitServiceClientBuilder.createOver(ActivityProviderAApi.class, baseUrl).buildClient();
    }
}
