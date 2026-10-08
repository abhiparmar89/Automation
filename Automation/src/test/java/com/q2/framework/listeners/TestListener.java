package com.q2.framework.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.q2.framework.factory.DriverFactory;
import com.q2.framework.utils.ReportManager;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger logger =
            LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {

        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass()
                .getRealClass()
                .getSimpleName();

        logger.info("Test Started: {}.{}", className, testName);

        ReportManager.createTest(testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        logger.info("Test Passed: {}", testName);

        ReportManager.getTest()
                .log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();

        String failureMessage =
                throwable != null ? throwable.getMessage() : "Unknown failure";

        String exceptionClass =
                throwable != null
                        ? throwable.getClass().getName()
                        : "UnknownException";

        logger.error(
                "Test Failed: {} | Failure: {} | Exception: {}",
                testName,
                failureMessage,
                exceptionClass
        );

        try {

            AppiumDriver driver = DriverFactory.getDriver();

            String screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BASE64);

            ReportManager.getTest()
                    .fail(
                            "Test Failed",
                            MediaEntityBuilder
                                    .createScreenCaptureFromBase64String(
                                            screenshot
                                    )
                                    .build()
                    );

        } catch (Exception e) {

            logger.error(
                    "Unable to capture screenshot: {}",
                    e.getMessage()
            );

            ReportManager.getTest()
                    .fail("Test Failed: " + failureMessage);
        }
    }

    @Override
    public void onFinish(ITestContext context) {

        ReportManager.getExtent().flush();

        logger.info("Test execution finished. Report generated.");
    }
}