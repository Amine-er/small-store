package com.errabi.microservice1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.RequestPartServletServerHttpRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HelloController {

    @Autowired
    Environment environment;

    @GetMapping("/hello")
    public String sayHello() throws UnknownHostException {
        String port = environment.getProperty("local.server.port");
        String hostname = InetAddress.getLocalHost().getHostName();
        System.out.println("Microservice 1 is running on hostname: " + hostname + " and port: " + port);
        return "Hello, World! from hostname: " + hostname + " and port: " + port;
    }
}