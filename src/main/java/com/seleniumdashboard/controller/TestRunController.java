package com.seleniumdashboard.controller;

import com.seleniumdashboard.service.TestRunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/test-runs") // Base API path
@CrossOrigin(origins = "*") // CORS enable
public class TestRunController {

    private final TestRunnerService testRunnerService;

    @Autowired
    public TestRunController(TestRunnerService testRunnerService) {
        this.testRunnerService = testRunnerService;
    }

    /**
     * Test execution endpoint
     * @param request - JSON request containing parameters
     * @return Test execution results
     */
    @PostMapping("/execute")
    public Map<String, Object> executeTest(@RequestBody Map<String, String> request) {
        return testRunnerService.runTest(
            request.get("className"),    // Optional: specific test class
            request.get("methodName"),  // Optional: specific test method 
            request.getOrDefault("browser", "chrome"), // Default to chrome
            request.get("project")       // Mandatory: project name
        );
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/status")
    public String serviceStatus() {
        return "Test Runner Service is Operational!";
    }
}