package com.errabi.customer.controller;

import com.errabi.customer.service.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/microservice2")
public class WebClientConsumerController {
    private final WebClientService webClientService;

    @GetMapping("/webclient-consume")
    public Mono<String> consumeHello() {
        log.info("Consuming hello from microservice1");
        return webClientService.consumeHello();
    }
}
