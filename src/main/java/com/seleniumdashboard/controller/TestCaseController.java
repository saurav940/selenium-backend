package com.seleniumdashboard.controller;

import com.seleniumdashboard.model.TestCaseInfo;
import com.seleniumdashboard.service.TestCaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/testcases")
@CrossOrigin(origins = "http://localhost:3000")
public class TestCaseController {

    private final TestCaseService testCaseService;

    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @GetMapping
    public ResponseEntity<?> getTestCases(
            @RequestParam(required = false) String project) {
        
        if (project == null || project.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "Project parameter is required"));
        }

        try {
            String projectPath = "C:/Users/saurav.kumar/eclipse-workspace/" + project;
            List<TestCaseInfo> testCases = testCaseService.getTestCasesFromTestNG(projectPath);
            return ResponseEntity.ok(testCases);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}