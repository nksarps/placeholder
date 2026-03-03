package com.automation.api.tests.comments;

import com.automation.api.base.SetUp;
import com.automation.api.testdata.CommentsData;
import com.automation.api.utils.Endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("POST Comments API Tests")
public class PostCommentsTests extends SetUp {

    @Test
    @DisplayName("Validate API creates a new comment provided valid data successfully")
    public void testCreateComment() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"postId\": " + CommentsData.DEFAULT_COMMENT_POST_ID + ",\n" +
                        "  \"name\": \"" + CommentsData.COMMENT_NAME + "\",\n" +
                        "  \"email\": \"" + CommentsData.COMMENT_EMAIL + "\",\n" +
                        "  \"body\": \"" + CommentsData.COMMENT_BODY + "\"\n" +
                        "}")
        .when()
                .post(Endpoints.COMMENTS)
        .then()
                .statusCode(201)
                .body("postId", equalTo(CommentsData.DEFAULT_COMMENT_POST_ID))
                .body("name", equalTo(CommentsData.COMMENT_NAME))
                .body("email", equalTo(CommentsData.COMMENT_EMAIL))
                .body("body", equalTo(CommentsData.COMMENT_BODY))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath(CommentsData.COMMENT_CREATE_RESPONSE_SCHEMA_PATH));
    }

    @Test
    @DisplayName("Validate API rejects comment creation with empty body")
    public void testCreateCommentWithEmptyBody() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{}")
        .when()
                .post(Endpoints.COMMENTS)
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 400 || statusCode == 422,
                "Expected status 400 or 422 for comment creation with empty body, got: " + statusCode);
    }
}
