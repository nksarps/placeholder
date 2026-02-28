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

@DisplayName("Comments API Tests")
public class CommentsTests extends SetUp {

    @Test
    @DisplayName("GET /comments - should return all comments")
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
    @DisplayName("GET /comments - should return all comments with headers validation")
    public void testGetAllCommentsWithHeaders() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.COMMENTS)
        .then()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("GET /comments/1 - should return single comment with expected fields")
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
                .body("body", notNullValue());
    }

    @Test
    @DisplayName("GET /comments?postId=1 - should return comments for the post")
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
    @DisplayName("GET /comments/99999 - invalid comment should return empty object or 404")
    public void testGetInvalidComment() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.commentById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();
        String body = resp.asString().trim();

        assertTrue(statusCode == 200 || statusCode == 404,
                "Expected status 200 or 404 for invalid comment, got: " + statusCode);

        if (statusCode == 200) {
            boolean isEmptyObject = "{}".equals(body) || body.isEmpty();
            assertTrue(isEmptyObject, "Expected empty object or empty body for non-existent comment, got: " + body);
        }
    }

    @Test
    @DisplayName("GET /comments/1 - validate Content-Type header and charset")
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
        assertTrue(contentType.toLowerCase().contains("application/json"), "Expected Content-Type to contain 'application/json' but was: " + contentType);
        if (contentType.toLowerCase().contains("charset")) {
            assertTrue(contentType.toLowerCase().contains("utf-8"), "Expected charset to be UTF-8 when present, but was: " + contentType);
        }
    }

    @Test
    @DisplayName("POST /comments - should create a new comment with status 201")
    public void testCreateComment() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"postId\": " + TestData.DEFAULT_POST_ID + ",\n" +
                        "  \"name\": \"" + TestData.COMMENT_NAME + "\",\n" +
                        "  \"email\": \"" + TestData.COMMENT_EMAIL + "\",\n" +
                        "  \"body\": \"" + TestData.COMMENT_BODY + "\"\n" +
                        "}")
        .when()
                .post(Endpoints.COMMENTS)
        .then()
                .statusCode(201)
                .body("postId", equalTo(TestData.DEFAULT_POST_ID))
                .body("name", equalTo(TestData.COMMENT_NAME))
                .body("email", equalTo(TestData.COMMENT_EMAIL))
                .body("body", equalTo(TestData.COMMENT_BODY))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("PUT /comments/1 - should update an existing comment with status 200")
    public void testUpdateComment() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"postId\": " + TestData.DEFAULT_POST_ID + ",\n" +
                        "  \"id\": " + TestData.DEFAULT_COMMENT_ID + ",\n" +
                        "  \"name\": \"" + TestData.UPDATED_COMMENT_NAME + "\",\n" +
                        "  \"email\": \"" + TestData.UPDATED_COMMENT_EMAIL + "\",\n" +
                        "  \"body\": \"" + TestData.UPDATED_COMMENT_BODY + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.commentById(TestData.DEFAULT_COMMENT_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_COMMENT_ID))
                .body("postId", equalTo(TestData.DEFAULT_POST_ID))
                .body("name", equalTo(TestData.UPDATED_COMMENT_NAME))
                .body("email", equalTo(TestData.UPDATED_COMMENT_EMAIL))
                .body("body", equalTo(TestData.UPDATED_COMMENT_BODY));
    }

    @Test
    @DisplayName("DELETE /comments/1 - should delete comment and return status 200 or 204")
    public void testDeleteComment() {
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.commentById(TestData.DEFAULT_COMMENT_ID))
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }
}
