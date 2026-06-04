package com.devops.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class HealthResponse {

    private String status;
    private String version;
    private String environment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public HealthResponse(String status, String version, String environment) {
        this.status = status;
        this.version = version;
        this.environment = environment;
        this.timestamp = LocalDateTime.now();
    }

    public String getStatus() { return status; }
    public String getVersion() { return version; }
    public String getEnvironment() { return environment; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
