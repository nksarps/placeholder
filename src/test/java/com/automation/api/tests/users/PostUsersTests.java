package com.automation.api.tests.users;

import com.automation.api.base.SetUp;
import com.automation.api.testdata.UsersData;
import com.automation.api.utils.Endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("POST Users API Tests")
public class PostUsersTests extends SetUp {

    @Test
    @DisplayName("Validate API creates a new user with valid data successfully")
    public void testCreateUser() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"username\": \"" + UsersData.USER_NAME + "\",\n" +
                        "  \"email\": \"" + UsersData.USER_EMAIL + "\",\n" +
                        "  \"phone\": \"" + UsersData.USER_PHONE + "\",\n" +
                        "  \"website\": \"" + UsersData.USER_WEBSITE + "\"\n" +
                        "}")
        .when()
                .post(Endpoints.USERS)
        .then()
                .statusCode(201)
                .body("username", equalTo(UsersData.USER_NAME))
                .body("email", equalTo(UsersData.USER_EMAIL))
                .body("phone", equalTo(UsersData.USER_PHONE))
                .body("website", equalTo(UsersData.USER_WEBSITE))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Validate API rejects user creation with empty body")
    public void testCreateUserWithEmptyBody() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{}")
        .when()
                .post(Endpoints.USERS)
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 400 || statusCode == 422,
                "Expected status 400 or 422 for user creation with empty body, got: " + statusCode);
    }
}
