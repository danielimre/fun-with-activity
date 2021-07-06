package com.funwithactivity.recommendationservice.example;

import com.funwithactivity.recommendationservice.example.client.ProcessorApi;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
public class RemoteProcessor implements Processor {
    @NonNull
    private final ProcessorApi client;

    @Override
    public String process(String input) {
        return client.process(new ProcessorApi.ProcessRequest(input))
            .subscribeOn(Schedulers.boundedElastic())
            .block()
            .getResult();
    }
}
