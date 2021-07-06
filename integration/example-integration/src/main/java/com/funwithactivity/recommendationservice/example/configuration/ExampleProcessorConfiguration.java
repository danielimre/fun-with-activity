package com.funwithactivity.recommendationservice.example.configuration;

import brave.http.HttpTracing;
import com.funwithactivity.recommendationservice.example.Processor;
import com.funwithactivity.recommendationservice.example.RemoteProcessor;
import com.funwithactivity.recommendationservice.example.client.ProcessorApi;
import com.hotels.molten.http.client.RetrofitServiceClientBuilder;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleProcessorConfiguration {
    @Value("${integration.processor.client.baseUrl}")
    private String processorBaseUrl;
    @Autowired
    private MeterRegistry meterRegistry;
    @Autowired
    private HttpTracing httpTracing;

    @Bean
    public Processor remoteProcessor() {
        return new RemoteProcessor(processorClient());
    }

    private ProcessorApi processorClient() {
        return RetrofitServiceClientBuilder.createOver(ProcessorApi.class, processorBaseUrl)
            .metrics(meterRegistry)
            .httpTracing(httpTracing)
            .buildClient();
    }
}
