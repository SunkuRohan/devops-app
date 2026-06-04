package com.devops.app.controller;

import com.devops.app.model.AppInfoResponse;
import com.devops.app.model.HealthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppController {

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Value("${app.environment:local}")
    private String environment;

    // Health check endpoint — used by Kubernetes liveness/readiness probes
    @GetMapping("/health")
    public ResponseEntity<HealthResponse> health() {
        return ResponseEntity.ok(new HealthResponse("ok", appVersion, environment));
    }

    // App info endpoint
    @GetMapping("/info")
    public ResponseEntity<AppInfoResponse> info() {
        return ResponseEntity.ok(new AppInfoResponse(
                "devops-app",
                appVersion,
                "Sample Spring Boot app for DevOps pipeline practice",
                "Rohan"
        ));
    }

    // Simple greeting endpoint — useful for testing deployments
    @GetMapping("/greet")
    public ResponseEntity<Map<String, String>> greet(
            @RequestParam(defaultValue = "World") String name) {
        return ResponseEntity.ok(Map.of(
                "message", "Hello, " + name + "!",
                "environment", environment
        ));
    }
}
