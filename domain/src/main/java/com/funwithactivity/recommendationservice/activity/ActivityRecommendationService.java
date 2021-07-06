package com.funwithactivity.recommendationservice.activity;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.funwithactivity.recommendationservice.person.Person;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Recommends activities using multiple providers. The recommendations from the first provider responding in time will be returned.
 */
@RequiredArgsConstructor
@Slf4j
public class ActivityRecommendationService {
    @NonNull
    private final List<ActivityRecommendationProvider> providers;

    /**
     * Recommends activities for a given person. The activities are returned in priority order.
     *
     * @param person the person to get recommendations for
     * @return the list of activities
     */
    public List<Activity> recommendActivitiesFor(Person person) {
        return Flux.fromIterable(providers)
            .flatMapDelayError(p -> Mono.fromCallable(() -> p.getRecommendationsFor(person)).subscribeOn(Schedulers.boundedElastic()), 3, 4)
            .timeout(Duration.ofSeconds(3))
            .doOnNext(r -> LOG.info("Retrieved {}", kv("recommendations", r)))
            .onErrorMap(e -> new IllegalStateException("Couldn't retrieve recommendation from any provider.", e))
            .blockFirst()
            .stream()
            .sorted(Comparator.comparing(Activity::getPriority).reversed())
            .collect(Collectors.toList());
    }
}
