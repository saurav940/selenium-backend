package com.seleniumdashboard.model;

public class TestCaseInfo {

    private String className;
    private String methodName;

    public TestCaseInfo() {
    }

    public TestCaseInfo(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
