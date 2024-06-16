package com.kursach;


import com.kursach.managers.DriverManager;
import com.kursach.managers.ManagerInitiallization;
import com.kursach.pages.ReqresPage;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FrontReqresPageTest {
    private static final DriverManager driverManager = DriverManager.getInstance();

    @BeforeClass
    public static void beforeClass(){
        ManagerInitiallization.initFramework();
        driverManager.getDriver().get("https://reqres.in/");
    }

    @Test
    @DisplayName("Проверка того, что при нажатии на кнопку отправки образца запроса результат такой же как и через API")
    public void test() {
        ReqresPage startPage = new ReqresPage();
        startPage.checkOpenPage()
                .clickOnButtonAndCheckAPI("List users", "get")
                .clickOnButtonAndCheckAPI("Single user", "get")
                .clickOnButtonAndCheckAPI("Single user not found", "get")
                .clickOnButtonAndCheckAPI("List <resource>", "get")
                .clickOnButtonAndCheckAPI("Single <resource>", "get")
                .clickOnButtonAndCheckAPI("Single <resource> not found", "get")
                .clickOnButtonAndCheckAPI("Create", "post")
                .clickOnButtonAndCheckAPI("Update", "put")
                .clickOnButtonAndCheckAPI("Update", "patch")
                .clickOnButtonAndCheckAPI("Delete", "delete")
                .clickOnButtonAndCheckAPI("Register - successful", "post")
                .clickOnButtonAndCheckAPI("Register - unsuccessful", "post")
                .clickOnButtonAndCheckAPI("Login - successful", "post")
                .clickOnButtonAndCheckAPI("Login - unsuccessful", "post")
                .clickOnButtonAndCheckAPI("Delayed response", "get");
    }
    @AfterClass
    public static void after() {
        ManagerInitiallization.quitFramework();
    }

}
