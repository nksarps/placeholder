package com.automation.api.tests.posts;

import com.automation.api.base.SetUp;
import com.automation.api.utils.Endpoints;
import com.automation.api.resources.TestData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("POST Posts API Tests")
public class PostPostsTests extends SetUp {

    @Test
    @DisplayName("Validate API creates a new post with valid data successfully")
    public void testCreatePost() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"title\": \"" + TestData.POST_TITLE + "\",\n" +
                        "  \"body\": \"" + TestData.POST_BODY + "\",\n" +
                        "  \"userId\": " + TestData.POST_USER_ID + "\n" +
                        "}")
        .when()
                .post(Endpoints.POSTS)
        .then()
                .statusCode(201)
                .body("title", equalTo(TestData.POST_TITLE))
                .body("body", equalTo(TestData.POST_BODY))
                .body("userId", equalTo(TestData.POST_USER_ID))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath(TestData.POST_CREATE_RESPONSE_SCHEMA_PATH));
    }

    @Test
    @DisplayName("Validate API handles post creation with empty body")
    public void testCreatePostWithEmptyBody() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{}")
        .when()
                .post(Endpoints.POSTS)
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 400 || statusCode == 422,
                "Expected status 400 or 422 for post creation with empty body, got: " + statusCode);
    }
}
