package com.errabi.microservice2.controller;

import com.errabi.microservice2.service.WebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class WebClientConsumerController {
    private final WebClientService webClientService;

    @GetMapping("/consume")
    public Mono<String> consumeHello() {
        return webClientService.consumeHello();
    }
}
