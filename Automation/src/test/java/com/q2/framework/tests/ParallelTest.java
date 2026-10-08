package com.q2.framework.tests;

import com.q2.framework.base.BaseTest;
import com.q2.framework.pages.ApiDemosPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParallelTest extends BaseTest {

    @Test
    public void testParallelSession1() {

        System.out.println(
                "Thread ID: " + Thread.currentThread().getId()
        );

        ApiDemosPage page = new ApiDemosPage();

        Assert.assertTrue(
                page.isPreferenceVisible(),
                "Preference element is not visible"
        );

        page.openPreference();
    }

    @Test
    public void testParallelSession2() {

        System.out.println(
                "Thread ID: " + Thread.currentThread().getId()
        );

        ApiDemosPage page = new ApiDemosPage();

        Assert.assertTrue(
                page.isAccessibilityVisible(),
                "Accessibility element is not visible"
        );

        page.openAccessibility();
    }
}