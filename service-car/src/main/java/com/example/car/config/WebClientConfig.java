package com.example.car.config;
import org.springframework.context.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebClientConfig {
    @Bean @LoadBalanced @ConditionalOnProperty(name="client.fixed-url",havingValue="false",matchIfMissing=true)
    WebClient.Builder loadBalancedBuilder() { return WebClient.builder(); }
    @Bean @ConditionalOnProperty(name="client.fixed-url",havingValue="true")
    WebClient.Builder fixedBuilder() { return WebClient.builder(); }
}
