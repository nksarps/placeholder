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

@DisplayName("POST Photos API Tests")
public class PostPhotosTests extends SetUp {

    @Test
    @DisplayName("Validate API creates a new photo with status 201")
    public void testCreatePhoto() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"albumId\": " + PhotosData.DEFAULT_PHOTO_ALBUM_ID + ",\n" +
                        "  \"title\": \"" + PhotosData.PHOTO_TITLE + "\",\n" +
                        "  \"url\": \"" + PhotosData.PHOTO_URL + "\",\n" +
                        "  \"thumbnailUrl\": \"" + PhotosData.PHOTO_THUMBNAIL_URL + "\"\n" +
                        "}")
        .when()
                .post(Endpoints.PHOTOS)
        .then()
                .statusCode(201)
                .body("albumId", equalTo(PhotosData.DEFAULT_PHOTO_ALBUM_ID))
                .body("title", equalTo(PhotosData.PHOTO_TITLE))
                .body("url", equalTo(PhotosData.PHOTO_URL))
                .body("thumbnailUrl", equalTo(PhotosData.PHOTO_THUMBNAIL_URL))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Validate API handles photo creation with missing thumbnailUrl field")
    public void testCreatePhotoWithMissingThumbnailUrl() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"albumId\": " + PhotosData.DEFAULT_PHOTO_ALBUM_ID + ",\n" +
                        "  \"title\": \"" + PhotosData.PHOTO_TITLE + "\",\n" +
                        "  \"url\": \"" + PhotosData.PHOTO_URL + "\"\n" +
                        "}")
        .when()
                .post(Endpoints.PHOTOS)
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 400 || statusCode == 422,
                "Expected status 400 or 422 for photo creation with missing thumbnailUrl, got: " + statusCode);
    }

    @Test
    @DisplayName("Validate API handles photo creation with empty body")
    public void testCreatePhotoWithEmptyBody() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{}")
        .when()
                .post(Endpoints.PHOTOS)
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 400 || statusCode == 422,
                "Expected status 400 or 422 for photo creation with empty body, got: " + statusCode);
    }
}
