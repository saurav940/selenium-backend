package com.seleniumdashboard.model;

import java.util.List;

public class TestRequest {
    private String project;
    private List<String> testCases;  // ✅ Supports multiple test cases
    private String browser;

    // Getters and Setters
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public List<String> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<String> testCases) {
        this.testCases = testCases;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
