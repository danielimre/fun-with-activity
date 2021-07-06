package com.funwithactivity.recommendationservice.web;

import static net.logstash.logback.argument.StructuredArguments.kv;

import com.funwithactivity.recommendationservice.example.Processor;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ExampleController {
    @Autowired
    private Processor processor;

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

    @PostMapping("/example/post-with-delegate")
    public ExampleResponse postWithDelegate(@RequestBody ExampleRequest request) {
        return new ExampleResponse(processor.process("delegated " + request.getData()) + " created");
    }

    @PostMapping("/example/process")
    public ProcessResponse process(@RequestBody ProcessRequest request) {
        LOG.info("Processing {}", kv("input", request.getInput()));
        return new ProcessResponse("processed " + request.getInput());
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

    @Value
    public static final class ProcessRequest {
        private final String input;
    }

    @Value
    public static final class ProcessResponse {
        private final String result;
    }
}
