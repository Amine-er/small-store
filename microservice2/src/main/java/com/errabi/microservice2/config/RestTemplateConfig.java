package com.errabi.microservice2.config;

import io.micrometer.tracing.Tracer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(Tracer tracer) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            if (tracer.currentSpan() != null) {
                var context = tracer.currentSpan().context();
                request.getHeaders().add("X-B3-TraceId", context.traceId());
                request.getHeaders().add("X-B3-SpanId", context.spanId());
                if (context.parentId() != null) {
                    request.getHeaders().add("X-B3-ParentSpanId", context.parentId());
                }
                request.getHeaders().add("X-B3-Sampled", context.sampled() != null ? context.sampled().toString() : "1");
            }
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
