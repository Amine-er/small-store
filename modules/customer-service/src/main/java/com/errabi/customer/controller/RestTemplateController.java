package com.errabi.customer.controller;

import com.errabi.customer.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/microservice2")
public class RestTemplateController {
    private final RestTemplateService restTemplateService;

    @RequestMapping("/rest-consume")
    public String consumeHello() {
        log.info("Consuming hello from microservice1 using RestTemplate");
        return restTemplateService.consumeHello();
    }
}
