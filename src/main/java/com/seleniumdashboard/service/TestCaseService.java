package com.seleniumdashboard.service;

import com.seleniumdashboard.model.TestCaseInfo;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestCaseService {

    public List<TestCaseInfo> getTestCasesFromTestNG(String projectPath) {
        List<TestCaseInfo> testCases = new ArrayList<>();

        try {
            File xmlFile = new File(projectPath + "/testng.xml");
            System.out.println("Looking for testng.xml at: " + xmlFile.getAbsolutePath());

            if (!xmlFile.exists()) {
                System.out.println("testng.xml not found!");
                return testCases;
            }

            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList classes = doc.getElementsByTagName("class");

            for (int i = 0; i < classes.getLength(); i++) {
                Element classElement = (Element) classes.item(i);
                String className = classElement.getAttribute("name");

                NodeList methods = classElement.getElementsByTagName("include");

                if (methods.getLength() == 0) {
                    testCases.add(new TestCaseInfo(className, "ALL"));
                } else {
                    for (int j = 0; j < methods.getLength(); j++) {
                        Element methodElement = (Element) methods.item(j);
                        String methodName = methodElement.getAttribute("name");
                        testCases.add(new TestCaseInfo(className, methodName));
                    }
                }
            }

            System.out.println("Found " + testCases.size() + " test cases");

        } catch (Exception e) {
            System.err.println("Error parsing testng.xml:");
            e.printStackTrace();
        }

        return testCases;
    }
}
