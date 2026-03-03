package com.automation.api.tests.users;

import com.automation.api.base.SetUp;
import com.automation.api.config.ApiConfig;
import com.automation.api.testdata.UsersData;
import com.automation.api.utils.Endpoints;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GET Users API Tests")
public class GetUsersTests extends SetUp {

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
                .get(Endpoints.userById(UsersData.DEFAULT_USER_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(UsersData.DEFAULT_USER_ID))
                .body("username", notNullValue())
                .body("email", notNullValue())
                .body("phone", notNullValue());
    }

    @Test
    @DisplayName("Validate API handles invalid user requests gracefully")
    public void testGetInvalidUser() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.userById(UsersData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid user, got: " + statusCode);
    }

    @Test
    @DisplayName("Validate API returns the correct Content-Type header and charset")
    public void testValidateContentType() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.userById(UsersData.DEFAULT_USER_ID))
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
}
