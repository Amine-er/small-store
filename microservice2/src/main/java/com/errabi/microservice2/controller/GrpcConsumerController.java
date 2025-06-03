package com.errabi.microservice2.controller;

import com.errabi.microservice2.service.GrpcClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/microservice2")
public class GrpcConsumerController {
    private final GrpcClientService grpcClientService;

    @GetMapping("/grpc-consume")
    public String consumeHello(@RequestParam(defaultValue = "World") String name) {
        return grpcClientService.consumeHello(name);
    }
}

