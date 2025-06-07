package com.errabi.microservice2.service;

import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestTemplateService {

    private final RestTemplate restTemplate;
    private final Tracer tracer;

    @Value("${microservice1.hello-url}")
    private String microServiceUrl;

    public String consumeHello() {
        // Log the current trace ID and span ID if available
        if (tracer.currentSpan() != null && tracer.currentSpan().context() != null) {
            log.info("Current traceId: {}, spanId: {}",
                    tracer.currentSpan().context().traceId(),
                    tracer.currentSpan().context().spanId());
        } else {
            log.warn("No active trace context found");
        }

        try {
            // Make the HTTP call
            String response = restTemplate.getForObject(microServiceUrl, String.class);

            // Log the response
            log.info("Received response from microservice1: {}", response);

            return response;

        } catch (Exception ex) {
            // Log the error details and return a fallback message
            log.error("Error while calling microservice1: {}", ex.getMessage(), ex);
            return "Service is unavailable. Please try again later.";
        }
    }
}
