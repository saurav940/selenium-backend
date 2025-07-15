package com.seleniumdashboard.service;

import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class TestRunnerService {

    // Maven path - Update this according to your system
    private static final String MAVEN_PATH = "C:\\apache-maven-3.8.6\\bin\\mvn.cmd";
    
    // Base workspace path
    private static final String WORKSPACE_PATH = "C:\\Users\\saurav.kumar\\eclipse-workspace\\";

    public Map<String, Object> runTest(String className, String methodName, String browser, String project) {
        Map<String, Object> response = new HashMap<>();
        Process process = null;
        
        try {
            String projectPath = WORKSPACE_PATH + project;
            File projectDir = new File(projectPath);
            
            if (!projectDir.exists()) {
                throw new FileNotFoundException("Project directory not found: " + projectPath);
            }

            ProcessBuilder builder = new ProcessBuilder();
            builder.directory(projectDir);
            builder.redirectErrorStream(true); // Merge error stream with input stream

            // Build Maven command
            if (className != null && !className.trim().isEmpty()) {
                String testTarget = methodName != null && !methodName.isEmpty() ? 
                                  className + "#" + methodName : className;
                                  
                builder.command(
                    MAVEN_PATH,
                    "clean", 
                    "test",
                    "-Dtest=" + testTarget,
                    "-Dbrowser=" + browser,
                    "-DsuiteXmlFile=testng.xml"
                );
            } else {
                builder.command(
                    MAVEN_PATH,
                    "clean", 
                    "test",
                    "-Dbrowser=" + browser,
                    "-Dsurefire.suiteXmlFiles=testng.xml"
                );
            }

            // Start process
            process = builder.start();
            
            // Capture logs
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            // Wait for process completion
            int exitCode = process.waitFor();
            
            // Build response
            response.put("status", exitCode == 0 ? "SUCCESS" : "FAILED");
            response.put("logs", output.toString());
            response.put("exitCode", exitCode);
            response.put("projectPath", projectPath);

        } catch (Exception e) {
            handleException(response, e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return response;
    }

    private void handleException(Map<String, Object> response, Exception e) {
        String errorMessage = "ERROR: " + e.getClass().getSimpleName() + 
                            " - " + e.getMessage();
        
        response.put("status", "ERROR");
        response.put("logs", errorMessage);
        response.put("exitCode", -1);
        
        // Log stack trace for debugging
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        response.put("stackTrace", sw.toString());
    }
}