package com.atmos.gateway_service.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/resilience")
public class ResilienceController {

    private final RestTemplate restTemplate;

    public ResilienceController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/circuitBreaker")
    public String home() {
        System.out.println("Inside Circuit Breaker");
        return restTemplate.getForObject(
                "http://localhost:8080/api/v1/rateLimit/weather",
                String.class
        );
    }

    @GetMapping("/retrydemo")
    public String retry() {
        System.out.println("inside retry mechanism");
        return restTemplate.getForObject(
                "http://localhost:8080/api/v1/rateLimit/weather",
                String.class
        );
    }
}