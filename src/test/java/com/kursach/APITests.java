package com.kursach;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APITests {

    @Test
    public void task1() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Map<String, Object> responseBody = response.jsonPath().getMap("");

        assertEquals(2, responseBody.get("page"), "Expected page to be 2");
        assertEquals(2, responseBody.get("total_pages"), "Expected total_pages to be 2");
        assertEquals(12, responseBody.get("total"), "Expected total to be 12");

        List<Map<String, Object>> data = (List<Map<String, Object>>) responseBody.get("data");
        List<Integer> ids = data.stream().map(d -> (Integer) d.get("id")).collect(Collectors.toList());
        assertEquals(ids.size(), ids.stream().distinct().count(), "Ожидаемые идентификаторы должны быть уникальными");

        boolean found = data.stream().anyMatch(d -> "Tobias".equals(d.get("first_name")) && "Funke".equals(d.get("last_name")));
        assertTrue(found, "Ожидается, что вы найдете пользователя с first_name=Tobias и last_name=Funke");
    }

    @Test
    public void userNotFound() {
        String responseBody =
                given()
                        .when()
                        .get("https://reqres.in/api/users/22")
                        .then()
                        .statusCode(404)
                        .extract()
                        .asString();

        assertEquals("{}", responseBody, "Expected response body to be empty JSON object {}");
    }

    @Test
    public void testUserDetails() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .extract().response();

        response.then()
                .body("data.id", equalTo(2))
                .body("data.email", equalTo("janet.weaver@reqres.in"))
                .body("data.first_name", equalTo("Janet"))
                .body("data.last_name", equalTo("Weaver"))
                .body("data.avatar", equalTo("https://reqres.in/img/faces/2-image.jpg"));
    }

    @Test
    public void createUser(){
        People people = new People("aslbek", "student");
        PeopleCreater peopleCreater = given()
                .contentType("application/json")
                .body(people)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(PeopleCreater.class);
        assertEquals(peopleCreater.getName(),people.getName());
        assertEquals(peopleCreater.getJob(), people.getJob());
    }
}
