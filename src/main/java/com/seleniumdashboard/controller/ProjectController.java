package com.seleniumdashboard.controller;

import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProjectController {

    // Base workspace path
    private final String baseWorkspacePath = "C:\\Users\\saurav.kumar\\eclipse-workspace";

    // 1. Return fixed list of allowed projects
    @GetMapping("/projects")
    public List<String> getProjects() {
        return Arrays.asList(
                "CapBankQA",
                "CSFB.online_Re_KYC",
                "AOO_Assisted_Module"
        );
    }

    // 2. Return browser for a given project by reading its config.properties
    @GetMapping("/browser")
    public List<String> getBrowserFromConfig(@RequestParam String project) {
        List<String> browsers = new ArrayList<>();
        try {
            // Full path to config.properties
            String configPath = baseWorkspacePath + "\\" + project + "\\configuration\\config.properties";
            File configFile = new File(configPath);

            if (!configFile.exists()) {
                return Collections.singletonList("⚠ Config file not found");
            }

            // Load properties
            FileInputStream fis = new FileInputStream(configFile);
            Properties props = new Properties();
            props.load(fis);

            String browser = props.getProperty("browser");

            if (browser != null && !browser.isBlank()) {
                browser = browser.trim().toLowerCase(); // Normalize casing
                browsers.add(browser);
            } else {
                browsers.add("⚠ Browser not set in config");
            }

        } catch (Exception e) {
            browsers.add("❌ Error: " + e.getMessage());
        }

        return browsers;
    }
}
