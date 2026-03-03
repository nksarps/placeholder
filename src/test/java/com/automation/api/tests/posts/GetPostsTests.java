package com.automation.api.tests.posts;

import com.automation.api.base.SetUp;
import com.automation.api.config.ApiConfig;
import com.automation.api.utils.Endpoints;
import com.automation.api.resources.TestData;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GET Posts API Tests")
public class GetPostsTests extends SetUp {

    @Test
    @DisplayName("Validate API returns all posts")
    public void testGetAllPosts() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.POSTS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("Validate API returns a valid post with expected fields")
    public void testGetSinglePost() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.postById(TestData.DEFAULT_POST_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_POST_ID))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("body", notNullValue())
                .body(matchesJsonSchemaInClasspath(TestData.POST_SCHEMA_PATH));
    }

    @Test
    @DisplayName("Validate API returns posts for a specific user ID")
    public void testGetPostsByUserId() {
        given()
                .spec(requestSpec)
                .queryParam("userId", TestData.DEFAULT_USER_ID)
        .when()
                .get(Endpoints.POSTS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(TestData.DEFAULT_USER_ID)));
    }

    @Test
    @DisplayName("Validate API handles invalid Posts requests gracefully")
    public void testGetInvalidPost() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.postById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid post, got: " + statusCode);
    }

    @Test
    @DisplayName("Validate returns correct Content-Type header and charset for a valid post")
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
        assertTrue(contentType.toLowerCase().contains(ApiConfig.getContentType()), "Expected Content-Type to contain 'application/json' but was: " + contentType);
        if (contentType.toLowerCase().contains("charset")) {
            assertTrue(contentType.toLowerCase().contains(ApiConfig.getCharset()), "Expected charset to be UTF-8 when present, but was: " + contentType);
        }
    }

    @Test
    @DisplayName("Validate API returns all posts with headers validation")
    public void testGetAllPostsWithHeaders() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.POSTS)
        .then()
                .statusCode(200)
                .header("Content-Type", equalTo(ApiConfig.getContentTypeWithCharset()))
                .header("Cache-Control", notNullValue())
                .body("size()", greaterThan(0));
    }
}
