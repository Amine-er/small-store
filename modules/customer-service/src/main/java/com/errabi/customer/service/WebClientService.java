package com.errabi.customer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import io.micrometer.tracing.Tracer;



@Service
@Slf4j
@RequiredArgsConstructor
public class WebClientService {
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;
    @Value("${microservice1.hello-url}")
    private String microServiceUrl;

    public Mono<String> consumeHello() {
        // Check if there is an active trace context
        if (tracer.currentSpan() != null && tracer.currentSpan().context() != null) {
            log.info("Current traceId: {}", tracer.currentSpan().context().traceId());
        } else {
            log.warn("No active trace context found");
        }
        return webClientBuilder.build()
                .get()
                .uri(microServiceUrl)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> log.info("Received response from microservice1: {}", response))
                .onErrorResume(org.springframework.web.reactive.function.client.WebClientResponseException.class, e -> {
                    // Log the error and return a fallback response
                    log.error("Error while calling microservice1: {}", e.getMessage());
                    return Mono.just("Service is unavailable. Please try again later.");
                });
    }
}
