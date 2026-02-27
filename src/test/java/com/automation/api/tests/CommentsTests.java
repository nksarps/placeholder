package com.automation.api.tests;

import com.automation.api.base.SetUp;
import com.automation.api.config.Endpoints;
import com.automation.api.config.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Comments API GET Tests")
public class CommentsTests extends SetUp {

    @Test
    @DisplayName("GET /comments - should return all comments")
    public void testGetAllComments() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.COMMENTS)
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", greaterThan(0));
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
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("postId", everyItem(equalTo(TestData.DEFAULT_COMMENT_POST_ID)));
    }
}
