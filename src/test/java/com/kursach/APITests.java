package com.kursach;


import com.kursach.base.BaseTest;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.Assert.*;


public class APITests extends BaseTest {

    @BeforeClass
    public static void setUo(){
        BaseTest.setUp();
        requestSpecification.basePath("/users");
    }

    @Test
    @DisplayName("Валидация списка пользователей через схему JSON")
    @Step
    public void checkListUsersValidate() {
        sendGetRequest(200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UsersSchema.json"));
    }

    @Test
    @DisplayName("Проверка данных пользователей через DTO")
    @Step
    public void checkListUsersDTO() {
        UsersPage usersPage = checkStatusCodeGet("https://reqres.in/api/users?page=2", 200)
                    .extract().as(UsersPage.class);
        assertEquals(usersPage.getPage().intValue(), 2);
        assertEquals(usersPage.getPer_page().intValue(), 6);
        assertEquals(usersPage.getTotal().intValue(), 12);
        assertEquals(usersPage.getTotal_pages().intValue(), 2);
        assertFalse(usersPage.getData().isEmpty());
        assertEquals(usersPage.getData().get(2).getFirst_name(), "Tobias");
        assertEquals(usersPage.getData().get(2).getLast_name(), "Funke");
    }

    @Test
    @DisplayName("Проверка данных одного пользователя")
    @Step
    public void testUserFromResponse() {
        UserData userData = checkStatusCodeGet("https://reqres.in/api/users/2", 200)
                .extract().body().jsonPath().getObject("data", UserData.class);
        assertThat("Идентификатор пользователя должен быть равен 2", userData.getId(), is(2));
        assertThat("Адрес электронной почты пользователя должен быть janet.weaver@reqres.in", userData.getEmail(), is("janet.weaver@reqres.in"));
        assertThat("Имя пользователя должно быть Джанет", userData.getFirst_name(), is("Janet"));
        assertThat("Фамилия пользователя должна быть Weaver", userData.getLast_name(), is("Weaver"));
        assertThat("Аватар пользователя должен быть корректным", userData.getAvatar(), is("https://reqres.in/img/faces/2-image.jpg"));
    }

   @Test
   @DisplayName("Проверка данных одного ресурса")
   @Step
    public void singleList() {
        ColorData resource = checkStatusCodeGet("https://reqres.in/api/unknown/2", 200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ColorSchema.json"))
                .extract().jsonPath().getObject("data", ColorData.class);

        assertThat("",resource.getId(),is(2));
        assertThat("", resource.getName(), is("fuchsia rose"));
        assertThat("", resource.getYear(), is(2001));
        assertThat("", resource.getColor(), is("#C74375"));
        assertThat("", resource.getPantone_value(),is("17-2031"));
    }

    @Test
    @DisplayName("Проверка отсутствия ресурса")
    @Step
    public void singleListNotFound() {
        checkStatusCodeGet("https://reqres.in/api/unknown/23", 404)
                .body(equalTo("{}"));
    }

    @Test
    @DisplayName("Проверка отсутствия пользователя")
    @Step
    public void userNotFound() {
        String responseBody = checkStatusCodeGet("https://reqres.in/api/users/22", 404)
                        .extract()
                        .asString();
        assertEquals("{}", responseBody, "Expected response body to be empty JSON object {}");
    }

    @Test
    @DisplayName("Создание пользователя через эндпоинт /api/users POST")
    @Step
    public void createUser(){
        People people = new People("morpheus", "leader");
        PeopleCreater peopleCreater = checkStatusCodePost(people,"https://reqres.in/api/users",201)
                .extract()
                .as(PeopleCreater.class);
        assertEquals(peopleCreater.getName(),people.getName());
        assertEquals(peopleCreater.getJob(), people.getJob());
    }

    @Test
    @DisplayName("Получение списка ресурсов")
    @Step
    public void listRecource(){
        List<UsersPage> usersPageList =  checkStatusCodeGet("https://reqres.in/api/unknown",200)
                .extract().jsonPath().getList("data", UsersPage.class);
        assertNotNull(usersPageList, "Список не должен быть null");
        assertFalse(usersPageList.isEmpty(), "Список не должен быть пустым");
        assertEquals(6, usersPageList.size(), "Ожидалось шесть элемента в списке");
        System.out.println(usersPageList);
    }


    @Test
    @DisplayName("Обновление пользователя через PUT")
    @Step
    public void putUser(){
        People people = new People("morpheus","zion resident");
        checkStatusCodePut(people,"https://reqres.in/api/users/2",200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"));
    }

    @Test
    @DisplayName("Частичное обновление пользователя через PATCH")
    @Step
    public void patch(){
        People people = new People("morpheus","zion resident");
        checkStatusCodePatch(people,"https://reqres.in/api/users/2",200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"));
    }

    @Test
    @DisplayName("Удаление пользователя")
    @Step
    public void deleteUser_thenSuccess() {
        deleteUser ("https://reqres.in/api/users/2",204)
                .body(isEmptyOrNullString());
    }

    @Test
    @DisplayName("Регистрация пользователя")
    @Step
    public void  registerUser(){
        String requestBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";
        postRegister(requestBody,"https://reqres.in/api/register",200)
                .body("id", equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Ошибка при регистрации пользователя")
    @Step
    public void  unsuccesRegisterUser(){
        String requestBody = "{\"email\": \"sydney@fife\"}";
        postRegister(requestBody,"https://reqres.in/api/register",400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    @DisplayName("Вход пользователя")
    @Step
    public void  loginUser(){
        String requestBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";
        postRegister(requestBody,"https://reqres.in/api/login",200)
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Ошибка при входе пользователя")
    @Step
    public void  unsuccesLoginUser(){
        String requestBody = "{\"email\": \"peter@klaven\"}";
        postRegister(requestBody,"https://reqres.in/api/login",400)
                .body("error", equalTo("Missing password"));
    }


    @Test
    @DisplayName("Получение отложенного ответа")
    @Step
    public void getDelayedResponse(){
        UsersPage usersPage = checkStatusCodeGet("https://reqres.in/api/users?delay=3",200)
                .extract().as(UsersPage.class);
        assertNotNull(usersPage, "Список не должен быть null");
        System.out.println(usersPage);
    }


}
