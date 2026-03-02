package com.automation.api.tests;

import com.automation.api.base.SetUp;
import com.automation.api.config.ApiConfig;
import com.automation.api.utils.Endpoints;
import com.automation.api.resources.TestData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Users API Tests")
public class UsersTests extends SetUp {

    @Test
    @DisplayName("Validate API returns all users")
    public void testGetAllUsers() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.USERS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("Validate API returns single user with expected fields")
    public void testGetSingleUser() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.userById(TestData.DEFAULT_USER_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_USER_ID))
                .body("username", notNullValue())
                .body("email", notNullValue())
                .body("phone", notNullValue());
    }

    @Test
    @DisplayName("Validate API contains user with username")
    public void testGetUserByUsername() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.USERS)
        .then()
                .statusCode(200)
                .body("username", hasItem("Bret"));
    }

    @Test
    @DisplayName("Validate API handles invalid user with 404 status")
    public void testGetInvalidUser() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.userById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid user, got: " + statusCode);
    }

    @Test
    @DisplayName("Validate API Content-Type header and charset")
    public void testValidateContentType() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.userById(TestData.DEFAULT_USER_ID))
        .then()
                .statusCode(200)
                .extract()
                .response();

        String contentType = resp.getHeader("Content-Type");
        assertNotNull(contentType, "Content-Type header should be present");
        assertTrue(contentType.toLowerCase().contains(ApiConfig.getContentType()), "Expected Content-Type to contain 'application/json' but was: " + contentType);
        if (contentType.toLowerCase().contains("charset")) {
            assertTrue(contentType.toLowerCase().contains(ApiConfig.getCharset()), "Expected charset to be UTF-8 when present, but was: " + contentType);
        }
    }

    @Test
    @DisplayName("Validate API creates a new user with status 201")
    public void testCreateUser() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"username\": \"" + TestData.USER_NAME + "\",\n" +
                        "  \"email\": \"" + TestData.USER_EMAIL + "\",\n" +
                        "  \"phone\": \"" + TestData.USER_PHONE + "\",\n" +
                        "  \"website\": \"" + TestData.USER_WEBSITE + "\"\n" +
                        "}")
        .when()
                .post(Endpoints.USERS)
        .then()
                .statusCode(201)
                .body("username", equalTo(TestData.USER_NAME))
                .body("email", equalTo(TestData.USER_EMAIL))
                .body("phone", equalTo(TestData.USER_PHONE))
                .body("website", equalTo(TestData.USER_WEBSITE))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Validate API updates an existing user with status 200")
    public void testUpdateUser() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"id\": " + TestData.DEFAULT_USER_ID + ",\n" +
                        "  \"username\": \"" + TestData.UPDATED_USER_NAME + "\",\n" +
                        "  \"email\": \"" + TestData.UPDATED_USER_EMAIL + "\",\n" +
                        "  \"phone\": \"" + TestData.UPDATED_USER_PHONE + "\",\n" +
                        "  \"website\": \"" + TestData.UPDATED_USER_WEBSITE + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.userById(TestData.DEFAULT_USER_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_USER_ID))
                .body("username", equalTo(TestData.UPDATED_USER_NAME))
                .body("email", equalTo(TestData.UPDATED_USER_EMAIL))
                .body("phone", equalTo(TestData.UPDATED_USER_PHONE))
                .body("website", equalTo(TestData.UPDATED_USER_WEBSITE));
    }

    @Test
    @DisplayName("Validate API deletes user and returns status 200 or 204")
    public void testDeleteUser() {
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.userById(TestData.DEFAULT_USER_ID))
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }
}
