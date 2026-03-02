package com.automation.api.tests.posts;

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

@DisplayName("PUT Posts API Tests")
public class PutPostsTests extends SetUp {

    @Test
    @DisplayName("Validate API updates an existing post successfully with valid data")
    public void testUpdatePost() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"id\": " + TestData.DEFAULT_POST_ID + ",\n" +
                        "  \"title\": \"" + TestData.UPDATED_POST_TITLE + "\",\n" +
                        "  \"body\": \"" + TestData.UPDATED_POST_BODY + "\",\n" +
                        "  \"userId\": " + TestData.DEFAULT_USER_ID + "\n" +
                        "}")
        .when()
                .put(Endpoints.postById(TestData.DEFAULT_POST_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_POST_ID))
                .body("title", equalTo(TestData.UPDATED_POST_TITLE))
                .body("body", equalTo(TestData.UPDATED_POST_BODY))
                .body("userId", equalTo(TestData.DEFAULT_USER_ID));
    }

    @Test
    @DisplayName("Validate API handles update of invalid Post gracefully")
    public void testUpdateInvalidPost() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"id\": " + TestData.INVALID_ID + ",\n" +
                        "  \"title\": \"" + TestData.UPDATED_POST_TITLE + "\",\n" +
                        "  \"body\": \"" + TestData.UPDATED_POST_BODY + "\",\n" +
                        "  \"userId\": " + TestData.DEFAULT_USER_ID + "\n" +
                        "}")
        .when()
                .put(Endpoints.postById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid post update, got: " + statusCode);
    }
}
