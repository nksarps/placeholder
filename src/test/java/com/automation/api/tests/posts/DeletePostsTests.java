package com.automation.api.tests.posts;

import com.automation.api.base.SetUp;
import com.automation.api.utils.Endpoints;
import com.automation.api.resources.TestData;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DELETE Posts API Tests")
public class DeletePostsTests extends SetUp {

    @Test
    @DisplayName("Validate API deletes a post that exists successfully")
    public void testDeletePost() {
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.postById(TestData.DEFAULT_POST_ID))
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    @DisplayName("Validate API handles deletion of invalid post gracefully")
    public void testDeleteInvalidPost() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.postById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid post deletion, got: " + statusCode);
    }
}
