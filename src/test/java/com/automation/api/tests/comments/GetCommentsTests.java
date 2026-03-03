package com.automation.api.tests.comments;

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

@DisplayName("GET Comments API Tests")
public class GetCommentsTests extends SetUp {

    @Test
    @DisplayName("Validate API returns all comments")
    public void testGetAllComments() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.COMMENTS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("Validate API returns all comments with headers validation")
    public void testGetAllCommentsWithHeaders() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.COMMENTS)
        .then()
                .statusCode(200)
                .header("Content-Type", equalTo(ApiConfig.getContentTypeWithCharset()))
                .header("Cache-Control", notNullValue())
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("Validate API returns a valid comment with its expected fields")
    public void testGetSingleComment() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.commentById(TestData.DEFAULT_COMMENT_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_COMMENT_ID))
                .body("postId", notNullValue())
                .body("name", notNullValue())
                .body("email", notNullValue())
                .body("body", notNullValue())
                .body(matchesJsonSchemaInClasspath(TestData.COMMENT_SCHEMA_PATH));
    }

    @Test
    @DisplayName("Validate API returns comments for a valid post ID")
    public void testGetCommentsByPostId() {
        given()
                .spec(requestSpec)
                .queryParam("postId", TestData.DEFAULT_COMMENT_POST_ID)
        .when()
                .get(Endpoints.COMMENTS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("postId", everyItem(equalTo(TestData.DEFAULT_COMMENT_POST_ID)));
    }

    @Test
    @DisplayName("Validate API handles request for non-existent comments gracefully")
    public void testGetInvalidComment() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.commentById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid comment, got: " + statusCode);
    }

    @Test
    @DisplayName("Validate API returns the correct Content-Type header and charset")
    public void testValidateContentType() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.commentById(TestData.DEFAULT_COMMENT_ID))
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
