package com.errabi.microservice2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class RestService {
    private final RestTemplate restTemplate;

    /*@Value("${microservice1.base-url}")
    private String microServiceUrl;*/

    public String consumeHello() {
        String microServiceUrl = "http://MICROSERVICE1/hello"; // Use Eureka service name directly
        return restTemplate.getForObject(microServiceUrl, String.class);
    }
}
