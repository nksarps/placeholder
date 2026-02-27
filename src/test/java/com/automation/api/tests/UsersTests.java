package com.automation.api.tests;

import com.automation.api.base.SetUp;
import com.automation.api.config.Endpoints;
import com.automation.api.config.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Users API GET Tests")
public class UsersTests extends SetUp {

    @Test
    @DisplayName("GET /users - should return all users")
    public void testGetAllUsers() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.USERS)
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("GET /users/1 - should return user with id=1")
    public void testGetSingleUser() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.userById(TestData.DEFAULT_USER_ID))
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_USER_ID))
                .body("username", notNullValue());
    }
}
