package com.uniandes.matching.web.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import io.micrometer.core.instrument.Timer;

@Configuration

public class MetricsConfig {

    @Bean
    public Timer ordenTimer(MeterRegistry registry) {
        return Timer.builder("orden.tiempo.total")
                .publishPercentiles(0.5, 0.95)
                .publishPercentileHistogram(true) // recomendable
                .register(registry);
    }
}

