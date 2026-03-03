package com.automation.api.tests.photos;

import com.automation.api.base.SetUp;
import com.automation.api.config.ApiConfig;
import com.automation.api.testdata.PhotosData;
import com.automation.api.utils.Endpoints;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GET Photos API Tests")
public class GetPhotosTests extends SetUp {

    @Test
    @DisplayName("Validate API returns all photos")
    public void testGetAllPhotos() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.PHOTOS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("Validate API returns single photo with expected fields")
    public void testGetSinglePhoto() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.photoById(PhotosData.DEFAULT_PHOTO_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(PhotosData.DEFAULT_PHOTO_ID))
                .body("albumId", notNullValue())
                .body("title", notNullValue())
                .body("url", notNullValue())
                .body("thumbnailUrl", notNullValue());
    }

    @Test
    @DisplayName("Validate API returns photos for albumId=1")
    public void testGetPhotosByAlbumId() {
        given()
                .spec(requestSpec)
                .queryParam("albumId", PhotosData.DEFAULT_PHOTO_ALBUM_ID)
        .when()
                .get(Endpoints.PHOTOS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("albumId", everyItem(equalTo(PhotosData.DEFAULT_PHOTO_ALBUM_ID)));
    }

    @Test
    @DisplayName("Validate API handles invalid photo with 404 status")
    public void testGetInvalidPhoto() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.photoById(PhotosData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertEquals(404, statusCode, "Expected status 404 for invalid photo, got: " + statusCode);
    }

    @Test
    @DisplayName("Validate API Content-Type header and charset")
    public void testValidateContentType() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.photoById(PhotosData.DEFAULT_PHOTO_ID))
        .then()
                .statusCode(200)
                .extract()
                .response();

        String contentType = resp.getHeader("Content-Type");
        assertNotNull(contentType, "Content-Type header should be present");
        assertTrue(contentType.toLowerCase().contains(ApiConfig.getContentType()), "Expected Content-Type to contain 'application/json' but was: " + contentType);
        if (contentType.toLowerCase().contains("charset")) {
            assertTrue(contentType.toLowerCase().contains(ApiConfig.getCharset()), "Expected charset to be UTF-8 when present, but was: " + contentType);
        }
    }
}
