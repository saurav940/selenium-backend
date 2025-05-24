package com.seleniumdashboard.service;

import com.seleniumdashboard.model.ProjectInfo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private static final String PROJECT_DIRECTORY = "C:\\Users\\saurav.kumar\\eclipse-workspace";

    public List<ProjectInfo> getAllProjects() {
        List<ProjectInfo> projects = new ArrayList<>();
        File folder = new File(PROJECT_DIRECTORY);

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    projects.add(new ProjectInfo(file.getName(), file.getAbsolutePath()));
                }
            }
        }

        return projects;
    }
}
