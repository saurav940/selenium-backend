package com.seleniumdashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seleniumdashboard.model.TestRequest;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin(origins = "*")
public class TestExecutionController {

    @PostMapping("/run")
    public ResponseEntity<String> runTest(@RequestBody TestRequest testRequest) {
        String project = testRequest.getProject();
        String testCase = testRequest.getTestCase();
        String browser = testRequest.getBrowser();

        // 👇 Trigger test execution (dummy response or actual logic)
        System.out.println("Running " + testCase + " on " + browser + " for " + project);

        // Here you can call a script or run Maven command
        return ResponseEntity.ok("Test started successfully for " + testCase);
    }
}
