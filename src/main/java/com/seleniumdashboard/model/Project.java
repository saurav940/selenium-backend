package com.seleniumdashboard.model;

import java.util.List;

public class Project {
    private String name;
    private List<String> testCases;

    public Project() {}
    public Project(String name, List<String> testCases) {
        this.name = name;
        this.testCases = testCases;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getTestCases() { return testCases; }
    public void setTestCases(List<String> testCases) { this.testCases = testCases; }
}
