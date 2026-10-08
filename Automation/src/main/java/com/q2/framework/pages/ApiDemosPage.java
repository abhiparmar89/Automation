package com.q2.framework.pages;

import com.q2.framework.factory.DriverFactory;
import io.appium.java_client.AppiumBy;

public class ApiDemosPage {

    public boolean isPreferenceVisible() {
        return DriverFactory.getDriver()
                .findElement(AppiumBy.accessibilityId("Preference"))
                .isDisplayed();
    }

    public void openPreference() {
        DriverFactory.getDriver()
                .findElement(AppiumBy.accessibilityId("Preference"))
                .click();
    }

    public boolean isAccessibilityVisible() {
        return DriverFactory.getDriver()
                .findElement(AppiumBy.accessibilityId("Accessibility"))
                .isDisplayed();
    }

    public void openAccessibility() {
        DriverFactory.getDriver()
                .findElement(AppiumBy.accessibilityId("Accessibility"))
                .click();
    }
}