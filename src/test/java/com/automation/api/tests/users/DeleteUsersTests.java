package com.automation.api.tests.users;

import com.automation.api.base.SetUp;
import com.automation.api.utils.Endpoints;
import com.automation.api.resources.TestData;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DELETE Users API Tests")
public class DeleteUsersTests extends SetUp {

    @Test
    @DisplayName("Validate API deletes a user that exists successfully")
    public void testDeleteUser() {
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.userById(TestData.DEFAULT_USER_ID))
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    @DisplayName("Validate API handles deletion of invalid user gracefully")
    public void testDeleteInvalidUser() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.userById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid user deletion, got: " + statusCode);
    }
}
