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

@DisplayName("PUT Users API Tests")
public class PutUsersTests extends SetUp {

    @Test
    @DisplayName("Validate API updates an existing user successfully with valid data")
    public void testUpdateUser() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"id\": " + UsersData.DEFAULT_USER_ID + ",\n" +
                        "  \"username\": \"" + UsersData.UPDATED_USER_NAME + "\",\n" +
                        "  \"email\": \"" + UsersData.UPDATED_USER_EMAIL + "\",\n" +
                        "  \"phone\": \"" + UsersData.UPDATED_USER_PHONE + "\",\n" +
                        "  \"website\": \"" + UsersData.UPDATED_USER_WEBSITE + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.userById(UsersData.DEFAULT_USER_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(UsersData.DEFAULT_USER_ID))
                .body("username", equalTo(UsersData.UPDATED_USER_NAME))
                .body("email", equalTo(UsersData.UPDATED_USER_EMAIL))
                .body("phone", equalTo(UsersData.UPDATED_USER_PHONE))
                .body("website", equalTo(UsersData.UPDATED_USER_WEBSITE));
    }

    @Test
    @DisplayName("Validate API handles update of invalid user gracefully")
    public void testUpdateInvalidUser() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"id\": " + UsersData.INVALID_ID + ",\n" +
                        "  \"username\": \"" + UsersData.UPDATED_USER_NAME + "\",\n" +
                        "  \"email\": \"" + UsersData.UPDATED_USER_EMAIL + "\",\n" +
                        "  \"phone\": \"" + UsersData.UPDATED_USER_PHONE + "\",\n" +
                        "  \"website\": \"" + UsersData.UPDATED_USER_WEBSITE + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.userById(UsersData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid user update, got: " + statusCode);
    }
}
