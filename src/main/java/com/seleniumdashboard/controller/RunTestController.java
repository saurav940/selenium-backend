package com.seleniumdashboard.controller;

import com.seleniumdashboard.model.RunTestRequest;
import com.seleniumdashboard.service.TestRunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RunTestController {

    @Autowired
    private TestRunnerService testRunnerService;

    @PostMapping("/run-tests")
    public Map<String, Object> runTest(@RequestBody RunTestRequest request) {
        String className = request.getTestCase();
        String methodName = "";  // Optional - if you want to run specific method use this
        String browser = request.getBrowser();
        String project = request.getProject(); // Optional for project-based execution

        return testRunnerService.runTest(className, methodName, browser, project);
    }
}
