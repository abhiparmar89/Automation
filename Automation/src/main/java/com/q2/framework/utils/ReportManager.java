package com.q2.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportManager {

    private static final ExtentReports extent;

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    static {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());

        String reportPath =
                "reports/TestReport_" + timestamp + ".html";

        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(reportPath);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    public static ExtentTest createTest(String testName) {

        ExtentTest test = extent.createTest(testName);

        extentTest.set(test);

        return test;
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static ExtentReports getExtent() {
        return extent;
    }
}