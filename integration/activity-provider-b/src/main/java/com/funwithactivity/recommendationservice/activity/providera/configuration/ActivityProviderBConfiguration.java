package com.funwithactivity.recommendationservice.activity.providera.configuration;

import com.funwithactivity.recommendationservice.activity.ActivityRecommendationProvider;
import com.funwithactivity.recommendationservice.activity.providera.RemoteActivityRecommendationProviderB;
import com.funwithactivity.recommendationservice.activity.providera.client.ActivityProviderBApi;
import com.hotels.molten.http.client.RetrofitServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivityProviderBConfiguration {
    @Value("${integration.activityProviderB.client.baseUrl}")
    private String baseUrl;

    @Bean
    public ActivityRecommendationProvider activityRecommendationProviderB() {
        return new RemoteActivityRecommendationProviderB(client());
    }

    private ActivityProviderBApi client() {
        return RetrofitServiceClientBuilder.createOver(ActivityProviderBApi.class, baseUrl).buildClient();
    }
}
