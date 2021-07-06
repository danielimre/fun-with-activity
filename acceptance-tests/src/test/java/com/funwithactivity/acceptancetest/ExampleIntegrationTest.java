package com.funwithactivity.acceptancetest;

import static com.funwithactivity.acceptancetest.common.JsonSupport.asJson;
import static com.funwithactivity.acceptancetest.common.JsonSupport.asJsonString;
import static com.funwithactivity.acceptancetest.boot.MockWebServer.asJsonBody;
import static com.funwithactivity.acceptancetest.boot.MockWebServer.mockServer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.funwithactivity.acceptancetest.boot.IntegrationTestBootstrap;
import com.funwithactivity.recommendationservice.example.client.ProcessorApi;
import com.funwithactivity.recommendationservice.web.ExampleController.ExampleRequest;
import com.funwithactivity.recommendationservice.web.ExampleController.ExampleResponse;
import org.junit.jupiter.api.Test;
import org.mockserver.model.MediaType;
import org.springframework.http.HttpStatus;

public class ExampleIntegrationTest extends IntegrationTestBootstrap {

    @Test
    void should_support_post() {
        var response = givenRequestWith()
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE)
            .auth().basic("user", "pass")
            .body(asJson(new ExampleRequest("data")))
            .when()
            .post("/example/post")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(APPLICATION_JSON_VALUE)
            .extract()
            .as(ExampleResponse.class);
        assertThat(response).isEqualTo(new ExampleResponse("data created"));
    }

    @Test
    void should_delegate_call_to_mock() {
        mockServer().when(request()
                .withMethod("POST")
                .withPath("/example/process")
                .withBody(asJsonBody(new ProcessorApi.ProcessRequest("delegated data"))),
            exactly(1))
            .respond(response()
                .withStatusCode(200)
                .withBody(asJsonString(new ProcessorApi.ProcessResponse("processed delegated data")), MediaType.APPLICATION_JSON));
        var response = givenRequestWith()
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE)
            .auth().basic("user", "pass")
            .body(asJson(new ExampleRequest("data")))
            .when()
            .post("/example/post-with-delegate")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(APPLICATION_JSON_VALUE)
            .extract()
            .as(ExampleResponse.class);
        assertThat(response).isEqualTo(new ExampleResponse("processed delegated data created"));
    }

}
