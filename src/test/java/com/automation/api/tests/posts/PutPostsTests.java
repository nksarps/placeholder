package com.automation.api.tests.posts;

import com.automation.api.base.SetUp;
import com.automation.api.testdata.PostsData;
import com.automation.api.utils.Endpoints;

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
                        "  \"id\": " + PostsData.DEFAULT_POST_ID + ",\n" +
                        "  \"title\": \"" + PostsData.UPDATED_POST_TITLE + "\",\n" +
                        "  \"body\": \"" + PostsData.UPDATED_POST_BODY + "\",\n" +
                        "  \"userId\": " + PostsData.POST_USER_ID + "\n" +
                        "}")
        .when()
                .put(Endpoints.postById(PostsData.DEFAULT_POST_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(PostsData.DEFAULT_POST_ID))
                .body("title", equalTo(PostsData.UPDATED_POST_TITLE))
                .body("body", equalTo(PostsData.UPDATED_POST_BODY))
                .body("userId", equalTo(PostsData.POST_USER_ID));
    }

    @Test
    @DisplayName("Validate API handles update of invalid Post gracefully")
    public void testUpdateInvalidPost() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"id\": " + PostsData.INVALID_ID + ",\n" +
                        "  \"title\": \"" + PostsData.UPDATED_POST_TITLE + "\",\n" +
                        "  \"body\": \"" + PostsData.UPDATED_POST_BODY + "\",\n" +
                        "  \"userId\": " + PostsData.POST_USER_ID + "\n" +
                        "}")
        .when()
                .put(Endpoints.postById(PostsData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertEquals(404, statusCode, "Expected status 404 for invalid post update, got: " + statusCode);
    }
}
