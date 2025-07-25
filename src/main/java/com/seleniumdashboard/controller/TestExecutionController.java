package com.seleniumdashboard.controller;

import com.seleniumdashboard.model.TestRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TestExecutionController {

    @PostMapping("/start-test")
    public ResponseEntity<String> startTest(@RequestBody TestRequest testRequest) {
        String project = testRequest.getProject();
        List<String> testCases = testRequest.getTestCases();  // ✅ Multiple test cases
        String browser = testRequest.getBrowser();

        System.out.println("✅ Starting Test Execution");
        System.out.println("➡ Project: " + project);
        System.out.println("➡ Browser: " + browser);
        System.out.println("➡ Test Cases: " + testCases);

        String projectPath = "C:\\Users\\saurav.kumar\\eclipse-workspace\\" + project;

        try {
            // ✅ Join test cases with comma
            String joinedTestCases = String.join(",", testCases);

            String command = String.format("cmd /c cd %s && mvn test -Dtest=%s -Dbrowser=%s",
                    projectPath, joinedTestCases, browser);

            System.out.println("🔧 Executing Command: " + command);

            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                System.out.println("Test execution failed with exit code: " + exitCode);
                return ResponseEntity.status(500).body("One or more tests failed.");
            }

            System.out.println("✅ All selected tests passed.");
            return ResponseEntity.ok("✅ All selected tests executed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Exception during test execution: " + e.getMessage());
        }
    }
}
