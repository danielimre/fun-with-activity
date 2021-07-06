package com.funwithactivity.recommendationservice.activity.providera.configuration;

import brave.http.HttpTracing;
import com.funwithactivity.recommendationservice.activity.ActivityRecommendationProvider;
import com.funwithactivity.recommendationservice.activity.providera.RemoteActivityRecommendationProviderB;
import com.funwithactivity.recommendationservice.activity.providera.client.ActivityProviderBApi;
import com.hotels.molten.http.client.RetrofitServiceClientBuilder;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivityProviderBConfiguration {
    @Value("${integration.activityProviderB.client.baseUrl}")
    private String baseUrl;
    @Autowired
    private MeterRegistry meterRegistry;
    @Autowired
    private HttpTracing httpTracing;

    @Bean
    public ActivityRecommendationProvider activityRecommendationProviderB() {
        return new RemoteActivityRecommendationProviderB(client());
    }

    private ActivityProviderBApi client() {
        return RetrofitServiceClientBuilder.createOver(ActivityProviderBApi.class, baseUrl)
            .metrics(meterRegistry)
            .httpTracing(httpTracing)
            .buildClient();
    }
}
