package com.automation.api.tests.comments;

import com.automation.api.base.SetUp;
import com.automation.api.utils.Endpoints;
import com.automation.api.resources.TestData;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DELETE Comments API Tests")
public class DeleteCommentsTests extends SetUp {

    @Test
    @DisplayName("Validate API deletes a comment that exists successfully")
    public void testDeleteComment() {
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.commentById(TestData.DEFAULT_COMMENT_ID))
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    @DisplayName("Validate API handles deletion of non-existent comments gracefully")
    public void testDeleteInvalidComment() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.commentById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid comment deletion, got: " + statusCode);
    }
}
