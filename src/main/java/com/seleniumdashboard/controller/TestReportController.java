package com.seleniumdashboard.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/report")
public class TestReportController {

    // HTML Report link endpoint
    @GetMapping("/link")
    public String getReportLink() {
        return "https://capitalsmallreport.netlify.app/#";
    }

    // PDF download endpoint
    @GetMapping("/download-pdf")
    public ResponseEntity<Resource> downloadPDF() {
        String pdfPath = "C:/Users/saurav.kumar/eclipse-workspace/CapBankQA/Reports/Test_Report.pdf";
        FileSystemResource file = new FileSystemResource(new File(pdfPath));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Test_Report.pdf")
                .body(file);
    }
}