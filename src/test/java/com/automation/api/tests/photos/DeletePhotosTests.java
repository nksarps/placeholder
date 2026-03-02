package com.automation.api.tests.photos;

import com.automation.api.base.SetUp;
import com.automation.api.utils.Endpoints;
import com.automation.api.resources.TestData;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DELETE Photos API Tests")
public class DeletePhotosTests extends SetUp {

    @Test
    @DisplayName("Validate API deletes photo and returns status 200 or 204")
    public void testDeletePhoto() {
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.photoById(TestData.DEFAULT_PHOTO_ID))
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    @DisplayName("Validate API handles deletion of non-existent photos gracefully")
    public void testDeleteInvalidPhoto() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.photoById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid photo deletion, got: " + statusCode);
    }
}
