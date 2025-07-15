package com.seleniumdashboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@RestController
public class BrowserController {

    private final List<String> configPaths = Arrays.asList(
            "C:\\Users\\saurav.kumar\\eclipse-workspace\\CapBankQA\\configuration\\config.properties",
            "C:\\Users\\saurav.kumar\\eclipse-workspace\\CSFB.online_Re_KYC\\configuration\\config.properties",
            "C:\\Users\\saurav.kumar\\eclipse-workspace\\AOO_Assisted_Module\\configuration\\config.properties"
    );

    @GetMapping("/api/browsers")
    public Map<String, String> getAllBrowsers() {
        Map<String, String> browserMap = new LinkedHashMap<>();

        for (String path : configPaths) {
            try (FileInputStream fis = new FileInputStream(path)) {
                Properties props = new Properties();
                props.load(fis);

                String browser = props.getProperty("browser", "Not Found").trim();
                String projectName = extractProjectName(path);

                browserMap.put(projectName, browser.isEmpty() ? "Not Found" : browser);
            } catch (IOException e) {
                String projectName = extractProjectName(path);
                browserMap.put(projectName, "Error: " + e.getMessage());
                System.err.println("⚠ Failed to read: " + path + " → " + e.getMessage());
            }
        }

        return browserMap;
    }

    private String extractProjectName(String fullPath) {
        File file = new File(fullPath);
        File projectDir = file.getParentFile().getParentFile(); // Go up 2 levels to project root
        return projectDir != null ? projectDir.getName() : "UnknownProject";
    }
}
