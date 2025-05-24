package com.seleniumdashboard.model;

public class RunTestRequest {
    private String project;      // Optional, in case you use project-specific paths
    private String testCase;
    private String browser;

    // Getters and Setters
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTestCase() {
        return testCase;
    }

    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
