package com.q2.framework.factory;

import com.q2.framework.utils.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static final ThreadLocal<AppiumDriver> driverThread =
            new ThreadLocal<>();

    public static void initDriver(String platform) {

        try {

            String serverUrl = ConfigReader.get("appiumServerUrl");

            if (platform.equalsIgnoreCase("android")) {

                UiAutomator2Options options = new UiAutomator2Options();

                options.setDeviceName(
                        ConfigReader.get("deviceName")
                );

                options.setAutomationName(
                        ConfigReader.get("automationName")
                );

                options.setApp(
                        ConfigReader.get("appPath")
                );

                AppiumDriver driver = new AndroidDriver(
                        new URL(serverUrl),
                        options
                );

                driverThread.set(driver);

            } else if (platform.equalsIgnoreCase("ios")) {

                XCUITestOptions options = new XCUITestOptions();

                options.setDeviceName(
                        ConfigReader.get("deviceName")
                );

                options.setAutomationName(
                        ConfigReader.get("automationName")
                );

                AppiumDriver driver = new IOSDriver(
                        new URL(serverUrl),
                        options
                );

                driverThread.set(driver);

            } else {

                throw new IllegalArgumentException(
                        "Unsupported platform: " + platform
                );
            }

        } catch (MalformedURLException e) {

            throw new RuntimeException(
                    "Invalid Appium server URL", e
            );
        }
    }

    public static AppiumDriver getDriver() {

        AppiumDriver driver = driverThread.get();

        if (driver == null) {
            throw new IllegalStateException(
                    "Appium driver has not been initialized for the current thread."
            );
        }

        return driver;
    }

    public static void quitDriver() {

        AppiumDriver driver = driverThread.get();

        if (driver != null) {
            driver.quit();
        }

        driverThread.remove();
    }
}