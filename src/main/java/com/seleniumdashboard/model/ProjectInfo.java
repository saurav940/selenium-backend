package com.seleniumdashboard.model;

public class ProjectInfo {
    private String name;
    private String path;

    public ProjectInfo(String name, String path) {
        this.name = name;
        this.path = path;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
