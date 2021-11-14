package com.lowry.target.casestudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfiguration {


    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://redsky-uat.perf.target.com/redsky_aggregations/v1/redsky/case_study_v1?key=3yUxt7WltYG7MFKPp7uyELi1K40ad2ys")
                .build();
    }
}
