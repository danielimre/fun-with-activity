package com.funwithactivity.acceptancetest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.funwithactivity.acceptancetest.boot.IntegrationTestBootstrap;
import com.funwithactivity.recommendationservice.web.ExampleController;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ExampleIntegrationTest extends IntegrationTestBootstrap {

    @Test
    void should_support_post() {
        var response = givenRequestWith()
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE)
            .auth().basic("user", "pass")
            .body(asJson(new ExampleController.ExampleRequest("data")))
            .when()
            .post("/post")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(APPLICATION_JSON_VALUE)
            .extract()
            .as(ExampleController.ExampleResponse.class);
        assertThat(response).isEqualTo(new ExampleController.ExampleResponse("data created"));
    }

}
