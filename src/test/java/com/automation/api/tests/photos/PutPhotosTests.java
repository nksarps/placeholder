package com.automation.api.tests.photos;

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

@DisplayName("PUT Photos API Tests")
public class PutPhotosTests extends SetUp {

    @Test
    @DisplayName("Validate API updates an existing photo with status 200")
    public void testUpdatePhoto() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"albumId\": " + TestData.DEFAULT_PHOTO_ALBUM_ID + ",\n" +
                        "  \"id\": " + TestData.DEFAULT_PHOTO_ID + ",\n" +
                        "  \"title\": \"" + TestData.UPDATED_PHOTO_TITLE + "\",\n" +
                        "  \"url\": \"" + TestData.UPDATED_PHOTO_URL + "\",\n" +
                        "  \"thumbnailUrl\": \"" + TestData.UPDATED_PHOTO_THUMBNAIL_URL + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.photoById(TestData.DEFAULT_PHOTO_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_PHOTO_ID))
                .body("albumId", equalTo(TestData.DEFAULT_PHOTO_ALBUM_ID))
                .body("title", equalTo(TestData.UPDATED_PHOTO_TITLE))
                .body("url", equalTo(TestData.UPDATED_PHOTO_URL))
                .body("thumbnailUrl", equalTo(TestData.UPDATED_PHOTO_THUMBNAIL_URL));
    }

    @Test
    @DisplayName("Validate API handles update of invalid photo gracefully")
    public void testUpdateInvalidPhoto() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"albumId\": " + TestData.DEFAULT_PHOTO_ALBUM_ID + ",\n" +
                        "  \"id\": " + TestData.INVALID_ID + ",\n" +
                        "  \"title\": \"" + TestData.UPDATED_PHOTO_TITLE + "\",\n" +
                        "  \"url\": \"" + TestData.UPDATED_PHOTO_URL + "\",\n" +
                        "  \"thumbnailUrl\": \"" + TestData.UPDATED_PHOTO_THUMBNAIL_URL + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.photoById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid photo update, got: " + statusCode);
    }
}
