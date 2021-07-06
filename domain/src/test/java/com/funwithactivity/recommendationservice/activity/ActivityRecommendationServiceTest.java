package com.funwithactivity.recommendationservice.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.funwithactivity.recommendationservice.person.Height;
import com.funwithactivity.recommendationservice.person.Mass;
import com.funwithactivity.recommendationservice.person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit test for {@link ActivityRecommendationService}.
 */
@ExtendWith(MockitoExtension.class)
public class ActivityRecommendationServiceTest {
    private static final Person PERSON = Person.builder()
        .weight(new Mass(BigDecimal.valueOf(55)))
        .height(new Height(BigDecimal.valueOf(143)))
        .birthDate(LocalDate.of(1932, 1, 2))
        .build();
    private ActivityRecommendationService service;
    @Mock
    private ActivityRecommendationProvider providerA;
    @Mock
    private ActivityRecommendationProvider providerB;

    @BeforeEach
    void initContext() {
        service = new ActivityRecommendationService(List.of(providerA, providerB));
    }

    @Test
    public void should_return_result_from_faster_provider() {
        // When
        when(providerA.getRecommendationsFor(PERSON)).thenAnswer(ie -> {
            Thread.sleep(100);
            return List.of(new Activity(2, "summaryA", "detailsA"));
        });
        when(providerB.getRecommendationsFor(PERSON)).thenReturn(List.of(new Activity(3, "summaryB", "detailsB")));
        // Then
        assertThat(service.recommendActivitiesFor(PERSON)).contains(new Activity(3, "summaryB", "detailsB"));
        verify(providerA).getRecommendationsFor(PERSON);
        verify(providerB).getRecommendationsFor(PERSON);
    }

    @Test
    public void should_delay_errors() {
        // When
        when(providerA.getRecommendationsFor(PERSON)).thenAnswer(ie -> {
            Thread.sleep(50);
            return List.of(new Activity(2, "summaryA", "detailsA"));
        });
        when(providerB.getRecommendationsFor(PERSON)).thenThrow(IllegalStateException.class);
        // Then
        assertThat(service.recommendActivitiesFor(PERSON)).contains(new Activity(2, "summaryA", "detailsA"));
        verify(providerA).getRecommendationsFor(PERSON);
        verify(providerB).getRecommendationsFor(PERSON);
    }

    @Test
    public void should_eventually_emit_errors_if_all_failed() {
        // When
        when(providerA.getRecommendationsFor(PERSON)).thenThrow(RuntimeException.class);
        when(providerB.getRecommendationsFor(PERSON)).thenThrow(RuntimeException.class);
        // Then
        assertThatThrownBy(() -> service.recommendActivitiesFor(PERSON))
            .isInstanceOf(IllegalStateException.class);
        verify(providerA).getRecommendationsFor(PERSON);
        verify(providerB).getRecommendationsFor(PERSON);
    }
}
