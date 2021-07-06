package com.funwithactivity.recommendationservice.activity.providera;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.funwithactivity.recommendationservice.activity.Activity;
import com.funwithactivity.recommendationservice.activity.providera.client.ActivityProviderAApi;
import com.funwithactivity.recommendationservice.person.Height;
import com.funwithactivity.recommendationservice.person.Mass;
import com.funwithactivity.recommendationservice.person.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

/**
 * Unit test for {@link RemoteActivityRecommendationProviderA}.
 */
@ExtendWith(MockitoExtension.class)
public class RemoteActivityRecommendationProviderATest {
    private static final BigDecimal RAW_HEIGHT = BigDecimal.valueOf(179);
    private static final BigDecimal RAW_WEIGHT = BigDecimal.valueOf(80);
    private static final String THE_TOKEN = "service1-dev";
    private static final LocalDate ANY_DATE = LocalDate.of(1993, 12, 13);
    @InjectMocks
    private RemoteActivityRecommendationProviderA provider;
    @Mock
    private ActivityProviderAApi client;

    @Test
    void should_map_request_and_response_to_client() {
        // Given
        var person = Person.builder()
            .height(new Height(RAW_HEIGHT))
            .weight(new Mass(RAW_WEIGHT))
            .birthDate(ANY_DATE)
            .build();
        // When
        when(client.recommend(new ActivityProviderAApi.ActivityRecommendationARequest(RAW_HEIGHT, RAW_WEIGHT, THE_TOKEN)))
            .thenReturn(Mono.just(List.of(
                new ActivityProviderAApi.ActivityRecommendationA(0.1F, "get up"),
                new ActivityProviderAApi.ActivityRecommendationA(0.4F, "run")
            )));
        // Then
        assertThat(provider.getRecommendationsFor(person)).contains(
            Activity.builder().priority(100).summary("get up").build(),
            Activity.builder().priority(400).summary("run").build()
        );
    }
}
