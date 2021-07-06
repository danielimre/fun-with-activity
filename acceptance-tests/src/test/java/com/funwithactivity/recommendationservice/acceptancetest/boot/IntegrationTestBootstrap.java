package com.funwithactivity.recommendationservice.acceptancetest.boot;

import com.funwithactivity.recommendationservice.RecommendationServiceApplication;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Sets up test context for black box integration testing.
 */
@ExtendWith({ServerUnderTestExtension.class, MockitoExtension.class})
@SpringBootTest(
    classes = RecommendationServiceApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ActiveProfiles("ci")
public class IntegrationTestBootstrap {

    @Value("http://localhost:${server.port}")
    private String baseUri;

    @BeforeEach
    public void initContext() {
        MockWebServer.reset();
    }

    protected RequestSpecification givenRequestWith() {
        return RestAssured.given()
            .baseUri(baseUri);
    }

}
