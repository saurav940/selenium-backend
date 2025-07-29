package com.seleniumdashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class BrowserController {

    // 🔧 Map project to config.properties file
    private final Map<String, String> projectConfigMap = new HashMap<>() {{
        put("CapBankQA", "C:\\Users\\saurav.kumar\\eclipse-workspace\\CapBankQA\\configuration\\config.properties");
        put("CSFB.online_Re_KYC", "C:\\Users\\saurav.kumar\\eclipse-workspace\\CSFB.online_Re_KYC\\configuration\\config.properties");
        put("AOO_Assisted_Module", "C:\\Users\\saurav.kumar\\eclipse-workspace\\AOO_Assisted_Module\\configuration\\config.properties");
    }};

    // ✅ GET: All projects with browser + version
    @GetMapping("/api/browsers")
    public Map<String, String> getAllBrowsers() {
        Map<String, String> browserMap = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : projectConfigMap.entrySet()) {
            String projectName = entry.getKey();
            String path = entry.getValue();

            try (FileInputStream fis = new FileInputStream(path)) {
                Properties props = new Properties();
                props.load(fis);

                String browserLine = props.getProperty("browser", "Not Found").trim();
                String[] browsers = browserLine.split(",");

                List<String> browserWithVersions = new ArrayList<>();
                for (String browser : browsers) {
                    String b = browser.trim();
                    String version = getBrowserVersion(b);
                    browserWithVersions.add(b + " " + version);
                }

                browserMap.put(projectName, String.join(", ", browserWithVersions));

            } catch (IOException e) {
                browserMap.put(projectName, "Error: " + e.getMessage());
                System.err.println("⚠ Error reading config for " + projectName + ": " + e.getMessage());
            }
        }

        return browserMap;
    }

    // ✅ GET: Single project's browser list with versions
    @GetMapping("/api/browsers/{projectName}")
    public ResponseEntity<Object> getBrowsersByProject(@PathVariable String projectName) {
        String configPath = projectConfigMap.get(projectName);

        if (configPath == null) {
            return ResponseEntity.status(404).body("❌ Project not found: " + projectName);
        }

        try (FileInputStream fis = new FileInputStream(configPath)) {
            Properties props = new Properties();
            props.load(fis);

            String browserLine = props.getProperty("browser", "Not Found").trim();
            String[] browsers = browserLine.split(",");

            Map<String, String> result = new LinkedHashMap<>();
            for (String browser : browsers) {
                String b = browser.trim();
                String version = getBrowserVersion(b);
                result.put(b, version);
            }

            return ResponseEntity.ok(result);

        } catch (IOException e) {
            return ResponseEntity.status(500).body("❌ Failed to read config: " + e.getMessage());
        }
    }

    // ✅ POST: Set browser for specific project
    @PostMapping("/api/set-browser")
    public ResponseEntity<String> setBrowser(@RequestBody Map<String, String> request) {
        String browser = request.get("browser");
        String project = request.get("project");

        if (browser == null || browser.isEmpty() || project == null || project.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ 'browser' and 'project' are required.");
        }

        String configPath = projectConfigMap.get(project);
        if (configPath == null) {
            return ResponseEntity.badRequest().body("❌ Unknown project: " + project);
        }

        try (FileInputStream fis = new FileInputStream(configPath)) {
            Properties props = new Properties();
            props.load(fis);
            props.setProperty("browser", browser);

            try (FileOutputStream fos = new FileOutputStream(configPath)) {
                props.store(fos, null);
            }

            return ResponseEntity.ok("✅ Browser updated to: " + browser + " for project: " + project);

        } catch (IOException e) {
            return ResponseEntity.status(500).body("❌ Failed to update config: " + e.getMessage());
        }
    }

    // ✅ GET: browser version by name
    @GetMapping("/api/browser-versions")
    public ResponseEntity<String> getVersionForBrowser(@RequestParam String browser) {
        if (browser == null || browser.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Browser is required");
        }

        String version = getBrowserVersion(browser);
        return ResponseEntity.ok(version);
    }

    // 🔍 Helper: Get version from registry
    private String getBrowserVersion(String browser) {
        try {
            String regCommand = switch (browser.toLowerCase()) {
                case "chrome" -> "reg query \"HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon\" /v version";
                case "edge" -> "reg query \"HKEY_CURRENT_USER\\Software\\Microsoft\\Edge\\BLBeacon\" /v version";
                case "firefox" -> "reg query \"HKEY_LOCAL_MACHINE\\Software\\Mozilla\\Mozilla Firefox\" /v CurrentVersion";
                default -> null;
            };

            if (regCommand == null) return "Unknown Browser";

            Process process = Runtime.getRuntime().exec(regCommand);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains("version")) {
                    String[] tokens = line.trim().split("\\s+");
                    return tokens[tokens.length - 1];
                }
            }
        } catch (IOException e) {
            System.err.println("⚠ Failed to read browser version: " + e.getMessage());
        }

        return "Version Unknown";
    }
}
