package com.automation.api.tests.comments;

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

@DisplayName("PUT Comments API Tests")
public class PutCommentsTests extends SetUp {

    @Test
    @DisplayName("Validate API updates an existing comment with valid data successfully")
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
    @DisplayName("Validate API handles updates of non-existent comments gracefully")
    public void testUpdateInvalidComment() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"postId\": " + TestData.DEFAULT_POST_ID + ",\n" +
                        "  \"id\": " + TestData.INVALID_ID + ",\n" +
                        "  \"name\": \"" + TestData.UPDATED_COMMENT_NAME + "\",\n" +
                        "  \"email\": \"" + TestData.UPDATED_COMMENT_EMAIL + "\",\n" +
                        "  \"body\": \"" + TestData.UPDATED_COMMENT_BODY + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.commentById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid comment update, got: " + statusCode);
    }
}
