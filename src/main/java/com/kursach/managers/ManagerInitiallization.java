package com.kursach.managers;

import java.util.concurrent.TimeUnit;

import static com.kursach.utils.Constatnt.IMPLICITLY_WAIT;
import static com.kursach.utils.Constatnt.PAGE_LOAD_TIMEOUT;


public class ManagerInitiallization {
    private static final ManagerPropertiesTest props = ManagerPropertiesTest.getInstance();
    private static final DriverManager driverManager = DriverManager.getInstance();

    public static void initFramework() {
        driverManager.getDriver().manage().deleteAllCookies();
        driverManager.getDriver().manage().window().maximize();
        driverManager.getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        driverManager.getDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(props.getProperty(PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
    }

    public static void quitFramework() {
        driverManager.quitDriver();

    }
}
