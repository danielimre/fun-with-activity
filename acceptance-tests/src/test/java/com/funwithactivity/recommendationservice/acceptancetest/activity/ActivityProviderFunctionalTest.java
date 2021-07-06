package com.funwithactivity.recommendationservice.acceptancetest.activity;

import static com.funwithactivity.recommendationservice.acceptancetest.boot.MockWebServer.asJsonBody;
import static com.funwithactivity.recommendationservice.acceptancetest.boot.MockWebServer.mockServer;
import static com.funwithactivity.recommendationservice.acceptancetest.common.JsonSupport.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.math.BigDecimal;
import java.util.List;

import com.funwithactivity.recommendationservice.acceptancetest.boot.IntegrationTestBootstrap;
import com.funwithactivity.recommendationservice.activity.providera.client.ActivityProviderAApi;
import com.funwithactivity.recommendationservice.activity.providera.client.ActivityProviderBApi;
import com.funwithactivity.recommendationservice.web.activity.ActivityRecommendation;
import org.junit.jupiter.api.Test;
import org.mockserver.matchers.MatchType;
import org.mockserver.model.Delay;
import org.mockserver.model.MediaType;
import org.springframework.http.HttpStatus;

public class ActivityProviderFunctionalTest extends IntegrationTestBootstrap {
    @Test
    void should_return_first_recommendation() {
        // Given
        mockServer().when(request()
                .withMethod("POST")
                .withPath("/activityprovidera/service1")
                .withBody(asJsonBody(
                    new ActivityProviderAApi.ActivityRecommendationARequest(BigDecimal.valueOf(184), BigDecimal.valueOf(74), "service1-dev"))),
            exactly(1))
            .respond(response()
                .withStatusCode(200)
                .withBody(asJsonString(List.of(
                    new ActivityProviderAApi.ActivityRecommendationA(0.3F, "least recommended activity A"),
                    new ActivityProviderAApi.ActivityRecommendationA(0.5F, "most recommended activity A")
                )), MediaType.APPLICATION_JSON));
        mockServer().when(request()
                .withMethod("POST")
                .withPath("/activityproviderb/service2")
                .withBody(json("{\"birthDate\": 371736000}", MatchType.ONLY_MATCHING_FIELDS)),
            exactly(1))
            .respond(response()
                .withDelay(Delay.milliseconds(300))
                .withStatusCode(200)
                .withBody(asJsonString(
                    new ActivityProviderBApi.ActivityRecommendationsB(List.of(
                        new ActivityProviderBApi.ActivityRecommendationB(232, "title 1", "most recommended activity B"),
                        new ActivityProviderBApi.ActivityRecommendationB(30, "title 2", "least recommended activity B")
                    ))), MediaType.APPLICATION_JSON));
        // When
        var response = givenRequestWith()
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE)
            .auth().basic("user", "pass")
            .when()
            .get("/activity/recommendation")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(APPLICATION_JSON_VALUE)
            .extract()
            .as(ActivityRecommendation.class);
        // Then
        assertThat(response).isEqualTo(new ActivityRecommendation(List.of(
            "most recommended activity A",
            "least recommended activity A"
        )));
    }
}
