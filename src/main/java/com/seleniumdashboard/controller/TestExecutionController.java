package com.seleniumdashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.seleniumdashboard.model.TestRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TestExecutionController {

    @PostMapping("/start-test")
    public ResponseEntity<String> startTest(@RequestBody TestRequest testRequest) {
        String project = testRequest.getProject();
        String testCase = testRequest.getTestCase();
        String browser = testRequest.getBrowser();

        System.out.println("✅ Starting Test");
        System.out.println("➡ Project: " + project);
        System.out.println("➡ Test Case: " + testCase);
        System.out.println("➡ Browser: " + browser);

        try {
            // Path to your automation Maven project
            String projectPath = "C:\\Users\\saurav.kumar\\eclipse-workspace\\" + project;

            // Build the command to execute Maven test
            String command = String.format("cmd /c cd %s && mvn test -Dtest=%s -Dbrowser=%s", 
                                            projectPath, testCase, browser);

            System.out.println("🔧 Executing Command: " + command);

            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("✅ Test execution finished successfully.");
                return ResponseEntity.ok("✅ Test started successfully for: " + testCase);
            } else {
                System.out.println("❌ Maven exited with code: " + exitCode);
                return ResponseEntity.status(500).body("❌ Test failed to execute. Maven exit code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Exception during test execution: " + e.getMessage());
        }
    }
}
