package com.funwithactivity.recommendationservice;

import com.hotels.molten.core.MoltenCore;
import com.hotels.molten.core.mdc.MoltenMDC;
import com.hotels.molten.core.metrics.MoltenMetrics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecommendationServiceApplication {

    public static void main(String[] args) {
        MoltenCore.initialize();
        MoltenMDC.initialize();
        MoltenMetrics.setDimensionalMetricsEnabled(true);
        SpringApplication.run(RecommendationServiceApplication.class, args);
    }

}
