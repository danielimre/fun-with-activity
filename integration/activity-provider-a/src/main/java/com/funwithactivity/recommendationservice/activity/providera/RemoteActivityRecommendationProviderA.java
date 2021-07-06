package com.funwithactivity.recommendationservice.activity.providera;

import java.util.List;
import java.util.stream.Collectors;

import com.funwithactivity.recommendationservice.activity.Activity;
import com.funwithactivity.recommendationservice.activity.ActivityRecommendationProvider;
import com.funwithactivity.recommendationservice.activity.providera.client.ActivityProviderAApi;
import com.funwithactivity.recommendationservice.person.Person;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Slf4j
public class RemoteActivityRecommendationProviderA implements ActivityRecommendationProvider {
    public static final float MAX_CONFIDENCE_TO_PRIORITY = 1000F;
    @NonNull
    private final ActivityProviderAApi client;

    @Override
    public List<Activity> getRecommendationsFor(Person person) {
        var request = new ActivityProviderAApi.ActivityRecommendationARequest(
            person.getHeight().inCms(),
            person.getWeight().inKgs(),
            "service1-dev"
        );
        LOG.info("calling provider A");
        return client.recommend(request).subscribeOn(Schedulers.boundedElastic())
            .block()
            .stream()
            .map(r -> Activity.builder()
                .priority(scaleToPriority(r.getConfidence()))
                .summary(r.getRecommendation())
                .build())
            .collect(Collectors.toList());

    }

    private int scaleToPriority(float confidence) {
        return (int) Math.floor(confidence * MAX_CONFIDENCE_TO_PRIORITY);
    }
}
