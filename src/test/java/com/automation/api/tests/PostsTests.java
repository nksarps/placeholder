package com.automation.api.tests;

import com.automation.api.base.SetUp;
import com.automation.api.utils.Endpoints;
import com.automation.api.utils.TestData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Posts API Tests")
public class PostsTests extends SetUp {

    @Test
    @DisplayName("GET /posts - should return all posts")
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
    @DisplayName("GET /posts/1 - should return single post with expected fields")
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
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(TestData.DEFAULT_USER_ID)));
    }

    @Test
    @DisplayName("GET /posts/99999 - invalid post should return empty object or 404")
    public void testGetInvalidPost() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.postById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();
        String body = resp.asString().trim();

        assertTrue(statusCode == 200 || statusCode == 404,
                "Expected status 200 or 404 for invalid post, got: " + statusCode);

        if (statusCode == 200) {
            boolean isEmptyObject = "{}".equals(body) || body.isEmpty();
            assertTrue(isEmptyObject, "Expected empty object or empty body for non-existent post, got: " + body);
        }
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
        if (contentType.toLowerCase().contains("charset")) {
            assertTrue(contentType.toLowerCase().contains("utf-8"), "Expected charset to be UTF-8 when present, but was: " + contentType);
        }
    }

    @Test
    @DisplayName("GET /posts - should return all posts with headers validation")
    public void testGetAllPostsWithHeaders() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.POSTS)
        .then()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("POST /posts - should create a new post with status 201")
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
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("PUT /posts/1 - should update an existing post with status 200")
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
    @DisplayName("DELETE /posts/1 - should delete post and return status 200 or 204")
    public void testDeletePost() {
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.postById(TestData.DEFAULT_POST_ID))
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }
}
