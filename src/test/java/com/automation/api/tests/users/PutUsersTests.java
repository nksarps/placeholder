package com.automation.api.tests.users;

import com.automation.api.base.SetUp;
import com.automation.api.utils.Endpoints;
import com.automation.api.resources.TestData;
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
    @DisplayName("Validate API handles update of invalid user gracefully")
    public void testUpdateInvalidUser() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"id\": " + TestData.INVALID_ID + ",\n" +
                        "  \"username\": \"" + TestData.UPDATED_USER_NAME + "\",\n" +
                        "  \"email\": \"" + TestData.UPDATED_USER_EMAIL + "\",\n" +
                        "  \"phone\": \"" + TestData.UPDATED_USER_PHONE + "\",\n" +
                        "  \"website\": \"" + TestData.UPDATED_USER_WEBSITE + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.userById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid user update, got: " + statusCode);
    }
}
