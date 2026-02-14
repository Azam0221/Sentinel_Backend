package com.example.sentinal.sentinel_backend.controller;

import com.example.sentinal.sentinel_backend.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DevOpsController {

    @Autowired
    private AgentService agentService;

    @PostMapping("/status")
    public ResponseEntity<Map<String, String>> checkSystemStatus() {

        boolean isKilled = agentService.shouldKill("agent-1");

        if (isKilled) {
            System.out.println("AGENT IS KILLED. REFUSING STATUS CHECK.");
            return ResponseEntity.status(403).body(Map.of(
                    "status", "OFFLINE",
                    "health", "0%",
                    "message", "AGENT TERMINATED BY SENTINEL KILL SWITCH."
            ));
        }

        return ResponseEntity.ok(Map.of(
                "status", "NOMINAL",
                "uptime", "42 hours",
                "health", "100%",
                "message", "Sentinel Backend is fully operational."
        ));
    }

    @PostMapping("/deploy")
    public ResponseEntity<Map<String, String>> triggerDeployment(@RequestBody(required = false) Map<String, Object> payload) {

        if (agentService.shouldKill("agent-1")) {
            return ResponseEntity.status(403).body(Map.of(
                    "status", "FAILED",
                    "message", "⛔ DEPLOYMENT BLOCKED. AGENT TERMINATED."
            ));
        }

        String targetEnv = (payload != null && payload.containsKey("env")) ? (String) payload.get("env") : "Production";
        System.out.println("DEPLOYMENT TRIGGERED FOR: " + targetEnv);

        return ResponseEntity.ok(Map.of(
                "status", "SUCCESS",
                "deploymentId", "build-" + new Random().nextInt(9999),
                "environment", targetEnv,
                "message", "Deployment sequence initiated successfully."
        ));
    }
}