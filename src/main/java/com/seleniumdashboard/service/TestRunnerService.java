package com.seleniumdashboard.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class TestRunnerService {

    public Map<String, Object> runTest(String className, String methodName, String browser, String project) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Optional: Customize path if needed using 'project'
            String projectPath = "C:\\Users\\saurav.kumar\\eclipse-workspace\\" + project;
            String testCommand = methodName == null || methodName.isEmpty()
                ? className
                : className + "#" + methodName;

            String command = String.format("cmd /c cd %s && mvn test -Dtest=%s -Dbrowser=%s",
                    projectPath, testCommand, browser);

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)
            );

            StringBuilder logs = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                logs.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            String status = (exitCode == 0) ? "SUCCESS" : "FAILED";

            response.put("status", status);
            response.put("logs", logs.toString());
            response.put("exitCode", exitCode);

        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("logs", e.getMessage());
            response.put("exitCode", -1);
        }

        return response;
    }
}
