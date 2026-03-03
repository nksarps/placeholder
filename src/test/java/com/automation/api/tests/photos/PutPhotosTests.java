package com.automation.api.tests.photos;

import com.automation.api.base.SetUp;
import com.automation.api.testdata.PhotosData;
import com.automation.api.utils.Endpoints;

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
                        "  \"albumId\": " + PhotosData.DEFAULT_PHOTO_ALBUM_ID + ",\n" +
                        "  \"id\": " + PhotosData.DEFAULT_PHOTO_ID + ",\n" +
                        "  \"title\": \"" + PhotosData.UPDATED_PHOTO_TITLE + "\",\n" +
                        "  \"url\": \"" + PhotosData.UPDATED_PHOTO_URL + "\",\n" +
                        "  \"thumbnailUrl\": \"" + PhotosData.UPDATED_PHOTO_THUMBNAIL_URL + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.photoById(PhotosData.DEFAULT_PHOTO_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(PhotosData.DEFAULT_PHOTO_ID))
                .body("albumId", equalTo(PhotosData.DEFAULT_PHOTO_ALBUM_ID))
                .body("title", equalTo(PhotosData.UPDATED_PHOTO_TITLE))
                .body("url", equalTo(PhotosData.UPDATED_PHOTO_URL))
                .body("thumbnailUrl", equalTo(PhotosData.UPDATED_PHOTO_THUMBNAIL_URL));
    }

    @Test
    @DisplayName("Validate API handles update of invalid photo gracefully")
    public void testUpdateInvalidPhoto() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"albumId\": " + PhotosData.DEFAULT_PHOTO_ALBUM_ID + ",\n" +
                        "  \"id\": " + PhotosData.INVALID_ID + ",\n" +
                        "  \"title\": \"" + PhotosData.UPDATED_PHOTO_TITLE + "\",\n" +
                        "  \"url\": \"" + PhotosData.UPDATED_PHOTO_URL + "\",\n" +
                        "  \"thumbnailUrl\": \"" + PhotosData.UPDATED_PHOTO_THUMBNAIL_URL + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.photoById(PhotosData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid photo update, got: " + statusCode);
    }
}
