package com.seleniumdashboard.controller;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*") // Add this to enable CORS
public class ProjectController 
{

    @GetMapping
    public List<String> getProjects() {
        File projectDir = new File("C:\\Users\\saurav.kumar\\eclipse-workspace");
        String[] directories = projectDir.list((current, name) -> new File(current, name).isDirectory());

        return directories != null ? Arrays.asList(directories) : Collections.emptyList();
    }
    
    
}


