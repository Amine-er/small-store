package com.errabi.microservice1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.tracing.Tracer;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/microservice1")
@Slf4j
public class HelloController {

    private final Tracer tracer;

    public HelloController(Tracer tracer) {
        this.tracer = tracer;
    }

    @Autowired
    Environment environment;

    @GetMapping("/hello")
    public String sayHello() throws UnknownHostException {
        String port = environment.getProperty("local.server.port");
        String hostname = InetAddress.getLocalHost().getHostName();
        log.info("Received request. Current traceId: {}, spanId: {}",
                tracer.currentSpan().context().traceId(),
                tracer.currentSpan().context().spanId());
        return "Hello, World! from hostname: " + hostname + " and port: " + port;
    }
}