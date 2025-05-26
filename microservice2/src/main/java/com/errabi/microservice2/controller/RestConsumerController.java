package com.errabi.microservice2.controller;

import com.errabi.microservice2.service.RestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestConsumerController {
    private final RestService restService;

    @GetMapping("/consume")
    public String consumeHello() {
        return restService.consumeHello();
    }
}
