package com.seleniumdashboard.service;

import org.springframework.stereotype.Service;
import java.io.File;
import java.util.*;

@Service
public class TestService {

    private static final String PROJECTS_DIR = "C:/Users/saurav.kumar/eclipse-workspace";

    public List<String> getProjectList() {
        File workspace = new File(PROJECTS_DIR);
        File[] files = workspace.listFiles(File::isDirectory);
        List<String> projectNames = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                projectNames.add(file.getName());
            }
        }
        return projectNames;
    }

    public List<String> getTestCases(String projectName) {
        File testDir = new File(PROJECTS_DIR + "/" + projectName + "/src/test/java");
        List<String> testCases = new ArrayList<>();
        fetchJavaFilesRecursively(testDir, testCases);
        return testCases;
    }

    private void fetchJavaFilesRecursively(File dir, List<String> list) {
        if (dir == null || !dir.exists()) return;
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                fetchJavaFilesRecursively(file, list);
            } else if (file.getName().endsWith("Test.java")) {
                list.add(file.getName().replace(".java", ""));
            }
        }
    }

    public String runTestCase(String projectName, String testCaseName, String browser) {
        try {
            String command = "cmd.exe /c cd " + PROJECTS_DIR + "/" + projectName +
                    " && mvn test -Dtest=" + testCaseName;
            Process process = Runtime.getRuntime().exec(command);
            return "Test execution started for: " + testCaseName + " on " + browser;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error executing test: " + e.getMessage();
        }
    }
}
