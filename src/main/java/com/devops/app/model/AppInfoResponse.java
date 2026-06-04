package com.devops.app.model;

public class AppInfoResponse {

    private String appName;
    private String version;
    private String description;
    private String author;

    public AppInfoResponse(String appName, String version, String description, String author) {
        this.appName = appName;
        this.version = version;
        this.description = description;
        this.author = author;
    }

    public String getAppName() { return appName; }
    public String getVersion() { return version; }
    public String getDescription() { return description; }
    public String getAuthor() { return author; }
}
