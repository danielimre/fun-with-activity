package com.funwithactivity.recommendationservice.activity.providera;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.funwithactivity.recommendationservice.activity.Activity;
import com.funwithactivity.recommendationservice.activity.ActivityRecommendationProvider;
import com.funwithactivity.recommendationservice.activity.providera.client.ActivityProviderBApi;
import com.funwithactivity.recommendationservice.person.Person;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Slf4j
public class RemoteActivityRecommendationProviderB implements ActivityRecommendationProvider {
    @NonNull
    private final ActivityProviderBApi client;

    @Override
    public List<Activity> getRecommendationsFor(Person person) {
        var request = ActivityProviderBApi.ActivityRecommendationBRequest.builder()
            .measurements(new ActivityProviderBApi.Measurements(person.getWeight().inPounds(), person.getHeight().inFeet()))
            .birthDate(person.getBirthDate().toEpochSecond(LocalTime.of(12, 0), ZoneOffset.UTC))
            .session(UUID.randomUUID().toString())
            .build();
        LOG.info("calling provider B");
        return client.recommend(request).subscribeOn(Schedulers.boundedElastic())
            .block()
            .getRecommendations()
            .stream()
            .map(r -> Activity.builder()
                .priority(r.getPriority())
                .summary(r.getTitle())
                .details(r.getDetails())
                .build())
            .collect(Collectors.toList());
    }
}
