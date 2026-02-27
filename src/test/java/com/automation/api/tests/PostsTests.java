package com.automation.api.tests;

import com.automation.api.base.SetUp;
import com.automation.api.config.Endpoints;
import com.automation.api.config.TestData;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Posts API GET Tests")
public class PostsTests extends SetUp {

    @Test
    @DisplayName("GET /posts - should return all posts")
    public void testGetAllPosts() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.POSTS)
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .time(lessThan(2000L), TimeUnit.MILLISECONDS)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("GET /posts/1 - should return single post with expected fields")
    public void testGetSinglePost() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.postById(TestData.DEFAULT_POST_ID))
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_POST_ID))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    @Test
    @DisplayName("GET /posts?userId=1 - should return posts for userId=1")
    public void testGetPostsByUserId() {
        given()
                .spec(requestSpec)
                .queryParam("userId", TestData.DEFAULT_USER_ID)
        .when()
                .get(Endpoints.POSTS)
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(TestData.DEFAULT_USER_ID)));
    }

    @Test
    @DisplayName("GET /posts/99999 - invalid post should return empty object")
    public void testGetInvalidPost() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.postById(TestData.INVALID_ID))
        .then()
                .statusCode(200)
                .extract()
                .response();

        String body = resp.asString().trim();
        boolean isEmptyObject = "{}".equals(body) || body.isEmpty();
        assertTrue(isEmptyObject, "Expected empty object or empty body for non-existent post, got: " + body);
    }

    @Test
    @DisplayName("GET /posts/1 - validate Content-Type header and charset")
    public void testValidateContentType() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.postById(TestData.DEFAULT_POST_ID))
        .then()
                .statusCode(200)
                .extract()
                .response();

        String contentType = resp.getHeader("Content-Type");
        assertNotNull(contentType, "Content-Type header should be present");
        assertTrue(contentType.toLowerCase().contains("application/json"), "Expected Content-Type to contain 'application/json' but was: " + contentType);
        // charset is optional - if present it should contain utf-8
        if (contentType.toLowerCase().contains("charset")) {
            assertTrue(contentType.toLowerCase().contains("utf-8"), "Expected charset to be UTF-8 when present, but was: " + contentType);
        }
    }
}
