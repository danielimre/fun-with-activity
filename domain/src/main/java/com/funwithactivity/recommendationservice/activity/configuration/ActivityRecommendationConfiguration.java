package com.funwithactivity.recommendationservice.activity.configuration;

import java.util.List;

import com.funwithactivity.recommendationservice.activity.ActivityRecommendationProvider;
import com.funwithactivity.recommendationservice.activity.ActivityRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivityRecommendationConfiguration {
    @Autowired
    private List<ActivityRecommendationProvider> providers;

    @Bean
    public ActivityRecommendationService activityRecommendationService() {
        return new ActivityRecommendationService(providers);
    }
}
