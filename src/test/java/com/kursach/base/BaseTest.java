package com.kursach.base;

import com.kursach.People;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.given;

public class BaseTest {

    public static RequestSpecification requestSpecification;
    static final String BASE_URL = "https://reqres.in/api";

    @BeforeClass
    public static void setUp() {
        requestSpecification = RestAssured.given()
                .baseUri(BASE_URL)
                .log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    public static ValidatableResponse checkStatusCodeGet(String url, int code) {
        return given(requestSpecification)
                .get(url)
                .then()
                .log().all()
                .statusCode(code);
    }

    static ValidatableResponse checkStatusCodeGet(int code){
        return given(requestSpecification)
                .get()
                .then()
                .log().all()
                .statusCode(code);
    }

    static ValidatableResponse checkStatusCodePost(String url, Object body, int code) {
        return given(requestSpecification)
                .body(body)
                .post(url)
                .then()
                .log().all()
                .statusCode(code);
    }
    public static ValidatableResponse checkStatusCodePost(Object body, String url, int code) {
        return given(requestSpecification)
                .contentType("application/json")
                .body(body)
                .post(url)
                .then()
                .log().all()
                .statusCode(code);
    }
    public static ValidatableResponse checkStatusCodePut(People user, String url, int code) {
        return given(requestSpecification)
                .contentType("application/json")
                .body(user)
                .put(url)
                .then()
                .log().all()
                .statusCode(code);
    }

    public static ValidatableResponse checkStatusCodePatch(People user, String url, int code) {
        return given(requestSpecification)
                .contentType("application/json")
                .body(user)
                .patch(url)
                .then()
                .log().all()
                .statusCode(code);
    }

    public static ValidatableResponse deleteUser(String url, int code){
        return given(requestSpecification)
                .when()
                .delete(url)
                .then()
                .statusCode(code);
    }

    public static ValidatableResponse sendGetRequest(int code){
        return given(requestSpecification)
                .get()
                .then()
                .log().all()
                .statusCode(code);
    }

    public static ValidatableResponse postRegister(String requestBody, String url, int code){
        return given(requestSpecification)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(url)
                .then()
                .log().all()
                .statusCode(code);

    }

    
}
