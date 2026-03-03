package com.automation.api.tests.comments;

import com.automation.api.base.SetUp;
import com.automation.api.testdata.CommentsData;
import com.automation.api.utils.Endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PUT Comments API Tests")
public class PutCommentsTests extends SetUp {

    @Test
    @DisplayName("Validate API updates an existing comment with valid data successfully")
    public void testUpdateComment() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"postId\": " + CommentsData.DEFAULT_COMMENT_POST_ID + ",\n" +
                        "  \"id\": " + CommentsData.DEFAULT_COMMENT_ID + ",\n" +
                        "  \"name\": \"" + CommentsData.UPDATED_COMMENT_NAME + "\",\n" +
                        "  \"email\": \"" + CommentsData.UPDATED_COMMENT_EMAIL + "\",\n" +
                        "  \"body\": \"" + CommentsData.UPDATED_COMMENT_BODY + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.commentById(CommentsData.DEFAULT_COMMENT_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(CommentsData.DEFAULT_COMMENT_ID))
                .body("postId", equalTo(CommentsData.DEFAULT_COMMENT_POST_ID))
                .body("name", equalTo(CommentsData.UPDATED_COMMENT_NAME))
                .body("email", equalTo(CommentsData.UPDATED_COMMENT_EMAIL))
                .body("body", equalTo(CommentsData.UPDATED_COMMENT_BODY));
    }

    @Test
    @DisplayName("Validate API handles updates of non-existent comments gracefully")
    public void testUpdateInvalidComment() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"postId\": " + CommentsData.DEFAULT_COMMENT_POST_ID + ",\n" +
                        "  \"id\": " + CommentsData.INVALID_ID + ",\n" +
                        "  \"name\": \"" + CommentsData.UPDATED_COMMENT_NAME + "\",\n" +
                        "  \"email\": \"" + CommentsData.UPDATED_COMMENT_EMAIL + "\",\n" +
                        "  \"body\": \"" + CommentsData.UPDATED_COMMENT_BODY + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.commentById(CommentsData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertEquals(404, statusCode, "Expected status 404 for invalid comment update, got: " + statusCode);
    }
}
