package com.kursach.pages;

import com.kursach.managers.ManagerPropertiesTest;
import io.qameta.allure.Step;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import java.util.List;

public class ReqresPage extends BasePage {

    private static final ManagerPropertiesTest props = ManagerPropertiesTest.getInstance();

    @FindBy(xpath = "/html/body/div[1]/main/div/h2[1]")
    private WebElement title;

    @FindBy(xpath = "//li[@data-id]")
    private List<WebElement> buttonList;

    @FindBy(xpath = "//pre[@data-key='output-response']")
    private WebElement outputResponse;

    @FindBy(xpath = "//pre[@data-key='output-request']")
    private WebElement outputRequest;

    @FindBy(xpath = "//span[@class='url']")
    private WebElement urlRequest;

    @FindBy(xpath = "//span[contains(@class,'response-code')]")
    private WebElement responseCode;

    @Step("Проверка открытия страницы Reqres")
    public ReqresPage checkOpenPage() {
        checkOpenPage("Test your front-end against a real API", title);
        return this;
    }

    @Step("Нажать на кнопку {nameButton} и проверить ответ API")
    public ReqresPage clickOnButtonAndCheckAPI(String nameButton, String httpMethod) {
        for (WebElement button : buttonList) {
            WebElement request = button.findElement(By.xpath("./a"));
            if (request.getText().equalsIgnoreCase(nameButton) && button.getAttribute("data-http").equalsIgnoreCase(httpMethod)) {
                moveToElement(button);
                button.click();
                waitUntilElementToBeVisible(outputResponse);
                Assertions.assertEquals("active", button.getAttribute("class"),"Кнопка не активирована");
                validateApiResponse(httpMethod, request);
                Assertions.assertEquals(request.getAttribute("href"), "https://reqres.in" + urlRequest.getText(),"URL запроса не совпадает");

                return this;
            }
        }
        Assertions.fail("Кнопка '" + nameButton + "' не найдена");
        return this;
    }

    private void validateApiResponse(String httpMethod, WebElement request) {
        String requestUrl = request.getAttribute("href");
        String requestBody = outputRequest.getText();
        String responseBody = outputResponse.getText();
        int expectedStatusCode = Integer.parseInt(responseCode.getText());

        switch (httpMethod.toLowerCase()) {
            case "get":
                Assertions.assertEquals(get(requestUrl), responseBody, "API response does not match");
                Assertions.assertEquals(getStatusCode(requestUrl), expectedStatusCode, "Status code does not match");
                break;
            case "post":
                compareResponses(post(requestUrl, requestBody), responseBody);
                Assertions.assertEquals(postStatusCode(requestUrl, requestBody), expectedStatusCode, "Status code does not match");
                break;
            case "put":
                compareResponses(put(requestUrl, requestBody), responseBody);
                Assertions.assertEquals(putStatusCode(requestUrl, requestBody), expectedStatusCode, "Status code does not match");
                break;
            case "patch":
                compareResponses(patch(requestUrl, requestBody), responseBody);
                Assertions.assertEquals(patchStatusCode(requestUrl, requestBody), expectedStatusCode, "Status code does not match");
                break;
            case "delete":
                Assertions.assertEquals(delete(requestUrl), responseBody, "API response does not match");
                Assertions.assertEquals(deleteStatusCode(requestUrl), expectedStatusCode, "Status code does not match");
                break;
            default:
                Assertions.fail("Incorrect HTTP method: " + httpMethod);
        }
    }
}