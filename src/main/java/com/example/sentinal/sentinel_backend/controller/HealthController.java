package com.example.sentinal.sentinel_backend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/ping")
    public Map<String, Object> ping() {
        return Map.of(
                "status", "Sentinel Backend is Online ",
                "timestamp", LocalDateTime.now(),
                "version", "1.0.0"
        );
    }
}