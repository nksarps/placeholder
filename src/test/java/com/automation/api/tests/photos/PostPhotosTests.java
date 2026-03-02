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

@DisplayName("POST Photos API Tests")
public class PostPhotosTests extends SetUp {

    @Test
    @DisplayName("Validate API creates a new photo with status 201")
    public void testCreatePhoto() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"albumId\": " + TestData.DEFAULT_PHOTO_ALBUM_ID + ",\n" +
                        "  \"title\": \"" + TestData.PHOTO_TITLE + "\",\n" +
                        "  \"url\": \"" + TestData.PHOTO_URL + "\",\n" +
                        "  \"thumbnailUrl\": \"" + TestData.PHOTO_THUMBNAIL_URL + "\"\n" +
                        "}")
        .when()
                .post(Endpoints.PHOTOS)
        .then()
                .statusCode(201)
                .body("albumId", equalTo(TestData.DEFAULT_PHOTO_ALBUM_ID))
                .body("title", equalTo(TestData.PHOTO_TITLE))
                .body("url", equalTo(TestData.PHOTO_URL))
                .body("thumbnailUrl", equalTo(TestData.PHOTO_THUMBNAIL_URL))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Validate API handles photo creation with missing thumbnailUrl field")
    public void testCreatePhotoWithMissingThumbnailUrl() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"albumId\": " + TestData.DEFAULT_PHOTO_ALBUM_ID + ",\n" +
                        "  \"title\": \"" + TestData.PHOTO_TITLE + "\",\n" +
                        "  \"url\": \"" + TestData.PHOTO_URL + "\"\n" +
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
