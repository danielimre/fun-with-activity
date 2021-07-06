package com.funwithactivity.recommendationservice.example.configuration;

import com.funwithactivity.recommendationservice.example.Processor;
import com.funwithactivity.recommendationservice.example.RemoteProcessor;
import com.funwithactivity.recommendationservice.example.client.ProcessorApi;
import com.hotels.molten.http.client.RetrofitServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleProcessorConfiguration {
    @Value("${integration.processor.client.baseUrl}")
    private String processorBaseUrl;

    @Bean
    public Processor remoteProcessor() {
        return new RemoteProcessor(processorClient());
    }

    private ProcessorApi processorClient() {
        return RetrofitServiceClientBuilder.createOver(ProcessorApi.class, processorBaseUrl).buildClient();
    }
}
