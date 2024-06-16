package com.kursach.managers;


import com.kursach.utils.Constatnt;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverManager {

    private WebDriver driver;
    private ManagerPropertiesTest propManager = ManagerPropertiesTest.getInstance();

    private static DriverManager INSTANCE = null;

    private DriverManager() {
    }

    public static DriverManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.close();
            driver.quit();
            driver = null;
        }
    }
    public void initDriver(){
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
    }
//    private void initDriver() {
//        System.setProperty("webdriver.chrome.driver", propManager.getProperty(Constatnt.PATH_CHROME_DRIVER_WINDOWS));
//        driver = new ChromeDriver();
//    }
}