package com.funwithactivity.recommendationservice.web;

import lombok.NonNull;
import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping("/")
    public String index() {
        return "Greetings!";
    }

    @GetMapping("/example/protected")
    public String protectMe() {
        return "Authenticated.";
    }

    @PostMapping("/example/post")
    public ExampleResponse post(@RequestBody ExampleRequest request) {
        return new ExampleResponse(request.getData() + " created");
    }

    @Value
    public static final class ExampleRequest {
        @NonNull
        private final String data;
    }

    @Value
    public static final class ExampleResponse {
        @NonNull
        private final String result;
    }

}
