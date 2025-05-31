package com.errabi.microservice2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class WebClientService {
    private final WebClient.Builder webClientBuilder;
    @Value("${microservice1.hello-url}")
    private String microServiceUrl;

    public Mono<String> consumeHello() {
        return webClientBuilder.build()
                .get()
                .uri(microServiceUrl)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(org.springframework.web.reactive.function.client.WebClientResponseException.class, e -> {
                    // Log the error and return a fallback response
                    System.err.println("Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
                    return Mono.just("Service is unavailable. Please try again later.");
                });
    }
}
